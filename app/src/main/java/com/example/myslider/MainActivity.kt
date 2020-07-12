package com.example.myslider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //第一項是 position 2
    private var currentPosition = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //因為有 clipChildren，前後各加上兩項，這樣做無限循環比較順暢
        //如果沒有 clipChildren，前後各加上一項就好
        val list = arrayListOf<SlideItem>()
        list.add(SlideItem(R.drawable.d))
        list.add(SlideItem(R.drawable.e))

        list.add(SlideItem(R.drawable.a))
        list.add(SlideItem(R.drawable.b))
        list.add(SlideItem(R.drawable.c))
        list.add(SlideItem(R.drawable.d))
        list.add(SlideItem(R.drawable.e))

        list.add(SlideItem(R.drawable.a))
        list.add(SlideItem(R.drawable.b))

        viewPager.adapter = SlideAdapter(list, viewPager)
        viewPager.currentItem = currentPosition

        //使用 clip 裁切效果產生預覽左右的功能、加上放大縮小動畫
        viewPager.clipToPadding = false
        viewPager.clipChildren = false
        viewPager.offscreenPageLimit = 1
        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }

        viewPager.setPageTransformer(transformer)

        //監聽頁面改變，並補正頁數，產生無限循環
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                // 不在此補正位置是因為左右有預覽的 item
                // 如果用 state = ViewPager2.SCROLL_STATE_IDLE 判斷並補正位置
                // 看起來會卡卡的
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // 判斷滾動是否快抵達，並進行頁面補正，前後兩項都補正，避免滾動太快造成問題
                // 如果沒有 clipChildren 只要前後一項補正即可
                if (positionOffset < 0.1) {
                    when (currentPosition) {
                        0 -> viewPager.setCurrentItem(list.size - 2, false)
                        1 -> viewPager.setCurrentItem(list.size - 3, false)
                    }
                } else if (positionOffset > 0.9) {
                    when (currentPosition) {
                        list.size - 2 -> viewPager.setCurrentItem(2, false)
                        list.size - 1 -> viewPager.setCurrentItem(1, false)
                    }
                }
            }

            override fun onPageSelected(position: Int) {
                currentPosition = position
            }
        })
    }
}

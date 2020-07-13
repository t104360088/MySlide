package com.example.myslider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2

import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {

    private lateinit var adapter: IntroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        initData()
        setIndicators()
        setCurrentIndicator(0)
        setListener()
    }

    private fun initData() {
        val list = arrayListOf<IntroItem>()
        list.add(IntroItem("A", "AA", R.drawable.intro))
        list.add(IntroItem("B", "BB", R.drawable.intro2))
        list.add(IntroItem("C", "CC", R.drawable.intro3))

        adapter = IntroAdapter(list)
        viewPager.adapter = adapter
    }

    private fun setIndicators() {
        val indicators = arrayOfNulls<ImageView>(adapter.itemCount)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        params.setMargins(8, 0, 8, 0)

        indicators.indices.forEach {
            indicators[it] = ImageView(applicationContext)
            indicators[it]?.apply {
                setImageDrawable(
                    ContextCompat.getDrawable(applicationContext,
                    R.drawable.indicator_inactive)
                )

                layoutParams = params
            }

            ll_container.addView(indicators[it])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = ll_container.childCount

        for (i in 0 until childCount) {
            val imageView = ll_container[i] as ImageView
            val res = if (i == index) R.drawable.indicator_active else R.drawable.indicator_inactive

            imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, res))
        }
    }

    private fun setListener() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        btn_next.setOnClickListener {
            val nextPosition = viewPager.currentItem + 1
            if (nextPosition < adapter.itemCount)
                viewPager.currentItem = nextPosition
            else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        tv_skip.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}

package com.wdretzer.doctordigital.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.wdretzer.doctordigital.*
import com.wdretzer.doctordigital.adapter.IntroAdapter


class OnboardingActivity : AppCompatActivity() {

    private val viewPager: ViewPager2 by lazy { findViewById(R.id.onboarding_view_pager) }

    private val btn_onboarding: Button
        get() = findViewById(R.id.btn_onboarding)

    private val text_skip: TextView
        get() = findViewById(R.id.text_skip)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        setupViewPager()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position == 2) {
                    btn_onboarding.setText("Get Started")
                    text_skip.isVisible = false


                } else {
                    btn_onboarding.setText("Next")
                    text_skip.isVisible = true
                }
            }
        })

        btn_onboarding.setOnClickListener {
            if (viewPager.currentItem == 2) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                viewPager.currentItem++
            }
        }

        text_skip.setOnClickListener {
            viewPager.currentItem = 2
        }
    }

    override fun onBackPressed() {

        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem -= 1
        }
    }

    private fun setupViewPager() {

        val listFragments = listOf(
            OnboardingScreen01Fragment(),
            OnboardingScreen02Fragment(),
            OnboardingScreen03Fragment()
        )

        viewPager.adapter = IntroAdapter(
            this, listFragments
        )
    }
}

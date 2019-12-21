package com.example.photoapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.photoapp.R
import com.example.photoapp.ui.adapters.PagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager.adapter = PagerAdapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(
            tabLayout,
            viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.text = "Home"
                    1 -> tab.text = "Collections"
                }
            }).attach()
    }

}

package com.example.photoapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.photoapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fr = FlowFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, fr).commit()
    }


}

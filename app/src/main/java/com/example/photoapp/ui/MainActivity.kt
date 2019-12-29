package com.example.photoapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.photoapp.R

class MainActivity : AppCompatActivity(), Router {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fr = MainFlowFragment() //todo change to router method
        supportFragmentManager.beginTransaction().add(R.id.main_flow_fragment, fr).commitNow()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        val currentFr =
            supportFragmentManager.findFragmentById(R.id.main_flow_fragment) as MainFlowFragment
        currentFr.onBackPressed()
    }

    override fun navigateTo() {
//        val fr = PhotoDetailsFragment()
//        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fr).commit()
    }
}

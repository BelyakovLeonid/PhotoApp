package com.example.photoapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.photoapp.R
import com.example.photoapp.ui.base.Router

class MainActivity : AppCompatActivity(), Router {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigateTo(FlowFragment())
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        val currentFr =
            supportFragmentManager.findFragmentById(R.id.main_flow_fragment) as FlowFragment
        currentFr.onBackPressed()
    }

    override fun navigateTo(destination: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_flow_fragment, destination)
            .commit()
    }

    override fun navigateWithSharedElement(
        sharedView: View,
        destination: Fragment
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

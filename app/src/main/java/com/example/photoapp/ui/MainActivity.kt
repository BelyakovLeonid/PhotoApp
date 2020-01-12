package com.example.photoapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.photoapp.R
import com.example.photoapp.ui.fragments.PagerFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class MainActivity : AppCompatActivity(), Router, KodeinAware {

    override val kodein by closestKodein()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val destinationFragment = PagerFragment()
            .also { it.router = this }
        navigateTo(destinationFragment, false)
    }

    override fun onBackPressed() {
        navigateBack()
    }

    override fun navigateTo(destination: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.test_one, R.anim.test_two)
            .replace(R.id.main_flow_fragment, destination)


        if (addToBackStack)
            transaction.addToBackStack(null)

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.commit()
    }

    override fun navigateWithSharedElement(
        sharedView: View,
        destination: Fragment,
        addToBackStack: Boolean
    ) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(false) // when true, there some problems with resyclerView shared transition
            .addSharedElement(sharedView, ViewCompat.getTransitionName(sharedView)!!)
            .replace(R.id.main_flow_fragment, destination)

        if (addToBackStack)
            transaction.addToBackStack(null)

        transaction.commit()
    }

    override fun navigateBack() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager
                .popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}

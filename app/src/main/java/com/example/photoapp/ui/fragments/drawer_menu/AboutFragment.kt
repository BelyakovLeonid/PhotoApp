package com.example.photoapp.ui.fragments.drawer_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.photoapp.R
import com.example.photoapp.ui.fragments.base.BaseFragment

class AboutFragment : BaseFragment() {

    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.toolbar)

//        setupListeners()
    }

    private fun setupListeners() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)

            supportActionBar?.apply {
                title = ""
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_back_black)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                router.navigateBack()
            }
        }
        return true
    }

}
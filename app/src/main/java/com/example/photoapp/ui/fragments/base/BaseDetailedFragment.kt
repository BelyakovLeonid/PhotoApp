package com.example.photoapp.ui.fragments.base

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.photoapp.R

abstract class BaseDetailedFragment : BaseFragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var shareIntent: Intent
    private lateinit var browserIntent: Intent

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.toolbar)
    }

    protected open fun bindToolbar() {
        setHasOptionsMenu(true)

        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)

            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_back_black)
                title = getFragmentTitle()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        toolbar.inflateMenu(R.menu.details_menu)
        toolbar.menu.findItem(R.id.toolbar_browser).isVisible = checkBrowser()
        toolbar.menu.findItem(R.id.toolbar_share).isVisible = checkShare()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                router.navigateBack()
            }
            R.id.toolbar_browser -> {
                startActivity(browserIntent)
            }
            R.id.toolbar_share -> {
                startActivity(shareIntent)
            }
        }
        return true
    }

    private fun checkBrowser(): Boolean {
        browserIntent =
            Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(getUrlString()) }

        val activities: List<ResolveInfo>? = activity?.packageManager?.queryIntentActivities(
            browserIntent,
            PackageManager.MATCH_DEFAULT_ONLY
        )

        return activities?.isNotEmpty() ?: false
    }

    private fun checkShare(): Boolean {
        shareIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, getUrlString())
            type = "text/html"
        }

        val activities: List<ResolveInfo>? = activity?.packageManager?.queryIntentActivities(
            shareIntent,
            PackageManager.MATCH_DEFAULT_ONLY
        )

        return activities?.isNotEmpty() ?: false
    }

    abstract fun getFragmentTitle(): CharSequence?

    abstract fun getUrlString(): String
}

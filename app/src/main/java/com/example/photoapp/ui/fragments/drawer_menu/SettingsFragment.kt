package com.example.photoapp.ui.fragments.drawer_menu

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.photoapp.R
import com.example.photoapp.ui.Router


class SettingsFragment : PreferenceFragmentCompat() {
    lateinit var browserIntent: Intent
    lateinit var router: Router

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)!!
        setHasOptionsMenu(true)

        (activity as AppCompatActivity).apply {
            setSupportActionBar(view.findViewById(R.id.toolbar))

            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_back_black)
                title = "Settings"
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findPreference<Preference>("PREF_GO_TO_UNSPLASH")?.setOnPreferenceClickListener {
            if (checkBrowser())
                startActivity(browserIntent)
            else {
                Toast.makeText(activity, "Please, download browser app", Toast.LENGTH_SHORT).show()
            }
            return@setOnPreferenceClickListener true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> router.navigateBack()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkBrowser(): Boolean {
        browserIntent =
            Intent(Intent.ACTION_VIEW).apply { data = Uri.parse("http://unsplash.com") }

        val activities: List<ResolveInfo>? = activity?.packageManager?.queryIntentActivities(
            browserIntent,
            PackageManager.MATCH_DEFAULT_ONLY
        )

        return activities?.isNotEmpty() ?: false
    }
}
package com.example.photoapp.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.GravityCompat
import androidx.transition.Slide
import androidx.viewpager2.widget.ViewPager2
import com.example.photoapp.R
import com.example.photoapp.local.adapters.PagerAdapter
import com.example.photoapp.ui.base.BaseFragment
import com.example.photoapp.ui.base.Router
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_pager.*

class PagerFragment : BaseFragment() {

    lateinit var router: Router

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        0 -> drawer_navigation_view.setCheckedItem(R.id.drawer_home)
                        else -> drawer_navigation_view.setCheckedItem(R.id.drawer_collections)
                    }
                }
            })

            adapter = PagerAdapter(childFragmentManager, lifecycle, router)
        }

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupNavigationDrawer()
        setHasOptionsMenu(true)

        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)

            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_menu_black)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        toolbar.inflateMenu(R.menu.home_menu)
    }

    private fun setupNavigationDrawer() {
        drawer_navigation_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.drawer_photo_of_day -> {
                    Toast.makeText(this.context, "one", Toast.LENGTH_SHORT).show()
                }
                R.id.drawer_home -> {
                    drawer.closeDrawer(GravityCompat.START)
                    viewPager.setCurrentItem(0, true)
                }
                R.id.drawer_collections -> {
                    drawer.closeDrawer(GravityCompat.START)
                    viewPager.setCurrentItem(1, true)
                }
                R.id.drawer_support -> {
                    Toast.makeText(this.context, "four", Toast.LENGTH_SHORT).show()
                }
                R.id.drawer_settings -> {
                    Toast.makeText(this.context, "five", Toast.LENGTH_SHORT).show()
                }
                R.id.drawer_info -> {
                    Toast.makeText(this.context, "six", Toast.LENGTH_SHORT).show()
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawer.openDrawer(GravityCompat.START)
            }
            R.id.toolbar_search -> {
                goToSearch()
            }
            R.id.toolbar_sort -> {
                showSortSubMenu()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showSortSubMenu() {
        val popup = PopupMenu(this.context!!, view!!.findViewById(R.id.toolbar_sort))
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.actions_menu, popup.menu)
        popup.show()
    }

    private fun goToSearch() {
        val destinationFragment = SearchFragment().also {
            it.router = router
            it.enterTransition = Slide(Gravity.END).setDuration(300)
        }
        router.navigateTo(destinationFragment)
    }
}
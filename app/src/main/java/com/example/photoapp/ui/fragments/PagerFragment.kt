package com.example.photoapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.GravityCompat
import androidx.transition.Slide
import androidx.viewpager2.widget.ViewPager2
import com.example.photoapp.R
import com.example.photoapp.local.adapters.PagerAdapter
import com.example.photoapp.ui.fragments.base.BaseFragment
import com.example.photoapp.ui.fragments.drawer_menu.AboutFragment
import com.example.photoapp.ui.fragments.drawer_menu.DonateFragment
import com.example.photoapp.ui.fragments.drawer_menu.SettingsFragment
import com.example.photoapp.ui.fragments.photo.random.RandomPhotoFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_pager.*
import org.kodein.di.KodeinAware

class PagerFragment : BaseFragment(), KodeinAware {

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
                        0 -> {
                            drawer_navigation_view.setCheckedItem(R.id.drawer_home)
                            showSortMenu(true)
                        }
                        else -> {
                            drawer_navigation_view.setCheckedItem(R.id.drawer_collections)
                            showSortMenu(false)
                        }
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
                    0 -> tab.text = resources.getString(R.string.fragment_home_title)
                    1 -> tab.text = resources.getString(R.string.fragment_collections_title)
                }
            }).attach()
    }

    private fun showSortMenu(show: Boolean) {
        toolbar.menu?.findItem(R.id.toolbar_sort)?.isVisible = show
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
                    openRandomPhoto()
                }
                R.id.drawer_home -> {
                    drawer.closeDrawers()
                    viewPager.setCurrentItem(0, true)
                }
                R.id.drawer_collections -> {
                    drawer.closeDrawers()
                    viewPager.setCurrentItem(1, true)
                }
                R.id.drawer_support -> {
                    openDonateFragment()
                }
                R.id.drawer_settings -> {
                    openSettingsFragment()
                }
                R.id.drawer_about -> {
                    openAboutFragment()
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
        (popup.menu as MenuBuilder).setOptionalIconsVisible(true)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.actions_menu, popup.menu)

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_latest -> commonViewModel.currentSortLiveData.postValue("latest")
                R.id.action_oldest -> commonViewModel.currentSortLiveData.postValue("oldest")
                R.id.action_popular -> commonViewModel.currentSortLiveData.postValue("popular")
            }
            return@setOnMenuItemClickListener true
        }
        popup.show()
    }

    private fun openRandomPhoto() {
        val destinationFragment = RandomPhotoFragment().also {
            it.router = router
            it.postponeEnterTransition()
            it.enterTransition = Slide(Gravity.START).setDuration(400)
        }

        router.navigateTo(destinationFragment)
    }

    private fun goToSearch() {
        val destinationFragment = SearchFragment()
            .also {
                it.router = router
                it.postponeEnterTransition()
                it.enterTransition = Slide(Gravity.START).setDuration(400)
            }

        router.navigateTo(destinationFragment)
    }

    private fun openAboutFragment() {
        val destinationFragment = AboutFragment()
            .also {
                it.router = router
                it.enterTransition = Slide(Gravity.START).setDuration(400)
            }

        router.navigateTo(destinationFragment)
    }

    private fun openDonateFragment() {
        val destinationFragment = DonateFragment()
            .also {
            it.router = router
                it.enterTransition = Slide(Gravity.START).setDuration(400)
            }

        router.navigateTo(destinationFragment)
    }

    private fun openSettingsFragment() {
        val destinationFragment = SettingsFragment()
            .also {
                it.enterTransition = Slide(Gravity.START).setDuration(400)
                it.router = router
        }

        router.navigateTo(destinationFragment)
    }
}
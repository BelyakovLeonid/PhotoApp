package com.example.photoapp.ui.fragments

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.photoapp.R
import com.example.photoapp.local.adapters.PagerSearchAdapter
import com.example.photoapp.ui.fragments.base.BaseSearchFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_pager_search.*


class SearchFragment : BaseSearchFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pager_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.adapter = PagerSearchAdapter(childFragmentManager, lifecycle, router)

        TabLayoutMediator(
            tabLayout,
            viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.text = "Photos"
                    1 -> tab.text = "Collections"
                }
            }).attach()
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

        startPostponedEnterTransition()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        toolbar.inflateMenu(R.menu.search_menu)
        toolbar.menu.findItem(R.id.menu_search)?.actionView?.apply {
            requestFocus()

            (this as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    commonSearchViewModel.executeQuery(query)
                    hideKeyBoard()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }
    }

    private fun hideKeyBoard() {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                hideKeyBoard()
                router.navigateBack()
            }
        }
        return true
    }

    override fun onDestroy() {
        viewModelStore.clear()
        super.onDestroy()
    }
}
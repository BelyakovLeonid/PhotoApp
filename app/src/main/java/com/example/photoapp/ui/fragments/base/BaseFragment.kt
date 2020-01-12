package com.example.photoapp.ui.fragments.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.photoapp.ui.Router
import com.example.photoapp.ui.viewmodels.CommonViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment : Fragment(), CoroutineScope, KodeinAware {
    override val kodein: Kodein by closestKodein()

    private lateinit var job: Job
    lateinit var router: Router
    lateinit var commonViewModel: CommonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        commonViewModel = ViewModelProviders.of(activity!!).get(CommonViewModel::class.java)
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
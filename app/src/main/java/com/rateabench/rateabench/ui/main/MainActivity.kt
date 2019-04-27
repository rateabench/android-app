package com.rateabench.rateabench.ui.main

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rateabench.rateabench.R
import com.rateabench.rateabench.models.Bench
import kotlinx.android.synthetic.main.main_activity.nav_host_fragment
import kotlinx.android.synthetic.main.main_activity_slide.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var hostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_slide)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        hostFragment = nav_host_fragment as NavHostFragment
        navController = hostFragment.navController

        val currentBenchObserver = Observer<Bench> { bench ->
            name.text = bench.toString()
        }
        viewModel.currentBench.observe(this, currentBenchObserver)

        list.adapter = ArrayAdapter<String>(
            this, android.R.layout.simple_list_item_1, listOf(
                "Hello",
                "This",
                "Is",
                "A",
                "List",
                "Hello",
                "This",
                "Is",
                "A",
                "List",
                "Hello",
                "This",
                "Is",
                "A",
                "List",
                "Hello",
                "This",
                "Is",
                "A",
                "List"
            )
        )
    }


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_benches -> {
                Timber.d("navigation_benches")
                //                navController.navigate(R.id.action_mapFragment_to_benches)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_map -> {
                Timber.d("navigation_map")
                //                navController.navigate(R.id.action_benches_to_mapFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}

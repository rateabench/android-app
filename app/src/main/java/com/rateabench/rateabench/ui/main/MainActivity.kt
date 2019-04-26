package com.rateabench.rateabench.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rateabench.rateabench.R
import kotlinx.android.synthetic.main.main_activity.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var hostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        hostFragment = nav_host_fragment as NavHostFragment
        navController = hostFragment.navController
        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        nav_view.menu.findItem(R.id.navigation_map).isChecked = true
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

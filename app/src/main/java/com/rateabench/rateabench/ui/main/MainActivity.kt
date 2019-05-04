package com.rateabench.rateabench.ui.main

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.rateabench.rateabench.R
import com.rateabench.rateabench.models.Bench
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_bench_info.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var hostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var listAdapter: ArrayAdapter<Bench>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        hostFragment = nav_host_fragment as NavHostFragment
        navController = hostFragment.navController
        appBarConfiguration = AppBarConfiguration.Builder(navController.graph).setDrawerLayout(drawer_layout).build()
        NavigationUI.setupWithNavController(toolbar, navController, drawer_layout)

        listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        viewable_benches_list.adapter = listAdapter

        viewModel.benchesInSight.observe(this, Observer<List<Bench>> { benches ->
            Timber.d("New benches: ${benches.size}")
            listAdapter.clear()
            listAdapter.addAll(benches)
        })
    }
}

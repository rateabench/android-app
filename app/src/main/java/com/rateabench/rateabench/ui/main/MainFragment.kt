package com.rateabench.rateabench.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.rateabench.rateabench.models.Bench
import kotlinx.android.synthetic.main.main_fragment.*
import timber.log.Timber


class MainFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var viewModel: MainViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        fun newInstance() = MainFragment()
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        setupMap()
        viewModel.fetchBenches()

        val benchesObserver = Observer<List<Bench>> { benches ->
            // Update the UI, in this case, a TextView.
            Timber.i(benches.toString())
        }
        viewModel.benchesLiveData.observe(this, benchesObserver)
    }


    private fun setupMap() {
        if (context?.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            setupLocation()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun setupLocation() {
        map.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            Timber.i("New location: $location")
            map.moveCamera(CameraUpdateFactory.newLatLng(location?.let { LatLng(it.latitude, it.longitude) }))
            map.animateCamera(CameraUpdateFactory.zoomTo(12f))
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Timber.i("Fine location permission granted")
                    setupLocation()
                } else {
                    Timber.w("Fine location permission denied")
                    Toast.makeText(context, "No permission :(", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(com.rateabench.rateabench.R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        context?.let {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(it)
        }
        mapview.onCreate(savedInstanceState)
        mapview.getMapAsync(this)
    }

    override fun onStart() {
        super.onStart()
        mapview.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapview.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapview?.onDestroy()
    }
}

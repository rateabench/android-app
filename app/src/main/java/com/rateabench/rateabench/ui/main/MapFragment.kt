package com.rateabench.rateabench.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Point
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import com.google.android.gms.maps.model.LatLngBounds
import com.rateabench.rateabench.R
import com.rateabench.rateabench.Utility
import com.rateabench.rateabench.models.Bench
import kotlinx.android.synthetic.main.main_fragment.*
import net.sharewire.googlemapsclustering.Cluster
import net.sharewire.googlemapsclustering.ClusterManager
import timber.log.Timber
import kotlin.math.ceil


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var viewModel: MainViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var clusterManager: ClusterManager<Bench>
    private lateinit var selected_bench: TextView
    private val allBenches: MutableList<Bench> = mutableListOf()


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.mapType = GoogleMap.MAP_TYPE_HYBRID
        setupMap()
        clusterManager = ClusterManager(requireContext(), map)
        clusterManager.setCallbacks(object : ClusterManager.Callbacks<Bench> {
            override fun onClusterClick(cluster: Cluster<Bench>): Boolean {

                // Zoom in more if camera is far away
                val zoomStep = ceil((map.maxZoomLevel - map.cameraPosition.zoom) / 5)
                Timber.d("Zoomstep: $zoomStep, Zoomlevel: ${map.cameraPosition.zoom}")
                map.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(cluster.latitude, cluster.longitude), map.cameraPosition.zoom + zoomStep
                    ), 350, null
                )
                return true
            }

            override fun onClusterItemClick(bench: Bench): Boolean {
                selected_bench.text = bench.toString()
                return true
            }

        })
        map.setOnCameraIdleListener {
            clusterManager.onCameraIdle()
            filterBenchesInSight()
        }

        viewModel.benchesLiveData.observe(this, Observer<List<Bench>> { benches ->
            allBenches.addAll(benches.filterNotNull())
            clusterManager.setItems(allBenches)
            filterBenchesInSight()
        })
    }

    // Filter in respect to the space slideUpPanel take up
    private fun filterBenchesInSight() {
        val cameraBounds = map.projection.visibleRegion.latLngBounds
        val panelHeight = resources.getDimension(R.dimen.slide_state_down_height)
        val SW = map.projection.fromScreenLocation(Point(0, (mapview.measuredHeight - panelHeight).toInt()))
        val bounds = LatLngBounds(SW, cameraBounds.northeast)
        viewModel.benchesInSight.postValue(allBenches.filter { bounds.contains(LatLng(it.lat, it.lng)) })
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
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location ->
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 12f))
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
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        selected_bench = requireActivity().findViewById(R.id.selected_bench)
        mapview.onCreate(savedInstanceState)
        if (!Utility.isNetworkAvailable(requireContext())) {
            Toast.makeText(requireContext(), R.string.no_internet, Toast.LENGTH_LONG).show()
        }
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

    override fun onStop() {
        super.onStop()
        mapview.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapview?.onDestroy()
    }
}

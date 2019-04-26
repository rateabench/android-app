package com.rateabench.rateabench

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.rateabench.rateabench.models.Bench

class BenchRenderer(context: Context?, map: GoogleMap, clusterManager: ClusterManager<Bench>) :
    DefaultClusterRenderer<Bench>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(bench: Bench, markerOptions: MarkerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bench))
    }

    override fun shouldRenderAsCluster(cluster: Cluster<Bench>) = cluster.size > 9
}
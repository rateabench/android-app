package com.rateabench.rateabench.models

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class Bench(val id: Int, val name: String, val lat: Double, val lng: Double) : ClusterItem {
    override fun getSnippet() = "Snippet for $name"

    override fun getTitle() = name

    override fun getPosition() = LatLng(lat, lng)
}

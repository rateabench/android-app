package com.rateabench.rateabench.models

import net.sharewire.googlemapsclustering.ClusterItem

data class Bench(val id: Int, val name: String, val lat: Double, val lng: Double) : ClusterItem {

    override fun getSnippet() = "Snippet for $name"
    override fun getTitle() = name
    override fun getLatitude() = lat
    override fun getLongitude() = lng
    override fun toString(): String {
        return "Bench(id=$id)"
    }


}

data class BenchResponse(val result: List<Bench>, val ok: String)

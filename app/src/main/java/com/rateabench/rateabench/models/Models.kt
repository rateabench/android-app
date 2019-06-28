package com.rateabench.rateabench.models

import net.sharewire.googlemapsclustering.ClusterItem

data class Bench(
    val id: Long,
    val creatorId: Long,
    val lat: Double,
    val lng: Double,
    val coordinateId: Long,
    val imageUrl: String,
    val ratings: List<Rating>
) : ClusterItem {
    private val name: String
        get() = "($lat, $lng)"

    override fun getSnippet() = "Snippet for $name"
    override fun getTitle() = name
    override fun getLatitude() = lat
    override fun getLongitude() = lng
}

data class Rating(val ratingTypeName: String, val value: Double)

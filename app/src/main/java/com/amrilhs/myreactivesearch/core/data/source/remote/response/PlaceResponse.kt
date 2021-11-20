package com.amrilhs.myreactivesearch.core.data.source.remote.response

import com.amrilhs.myreactivesearch.core.domain.model.PlacesItem
import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    @field:SerializedName("features")
    val features: List<PlacesItem>
)

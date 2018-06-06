package com.android.szparag.flighter.flightsbrowser.models.remote

import com.google.gson.annotations.SerializedName
data class Summary(
    @SerializedName("price") val price: Price,
    @SerializedName("newRoute") val newRoute: Boolean
)
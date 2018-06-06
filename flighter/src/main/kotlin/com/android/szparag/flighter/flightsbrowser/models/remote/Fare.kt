package com.android.szparag.flighter.flightsbrowser.models.remote

import com.google.gson.annotations.SerializedName
data class Fare(
    @SerializedName("outbound") val outbound: Outbound,
    @SerializedName("summary") val summary: Summary
)
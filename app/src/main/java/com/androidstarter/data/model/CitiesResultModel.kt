package com.androidstarter.data.model


import com.androidstarter.data.BaseResponse
import com.google.gson.annotations.SerializedName

data class CitiesResultModel(
    @SerializedName("geonames")
    val geonames: List<Geoname>,
    @SerializedName("totalResultsCount")
    val totalResultsCount: Int
) : BaseResponse()
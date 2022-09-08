package me.gilo.woodroid.models.countries


import com.google.gson.annotations.SerializedName

data class CountriesResponseItem(
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("states")
    val states: List<CountryState>
)

data class CountryState(
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String
)
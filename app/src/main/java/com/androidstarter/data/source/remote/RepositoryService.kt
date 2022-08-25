package com.androidstarter.data.source.remote

import com.androidstarter.data.model.CitiesResultModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoryService {
    @GET("searchJSON")
    suspend fun searchCities(
        @Query("name_starts") query: String,
        @Query("maxRows") maxRows: Int = 10,
        @Query("username") username: String = "keep_truckin"
    ): Response<CitiesResultModel>
}
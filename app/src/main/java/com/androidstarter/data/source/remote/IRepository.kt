package com.androidstarter.data.source.remote

import com.androidstarter.data.ApiResponse
import com.androidstarter.data.model.CitiesResultModel

interface IRepository {
    suspend fun searchCities(query: String): ApiResponse<CitiesResultModel>
}
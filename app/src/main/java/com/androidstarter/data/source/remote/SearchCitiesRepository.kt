package com.androidstarter.data.source.remote

import com.androidstarter.data.ApiResponse
import com.androidstarter.data.BaseRepository
import com.androidstarter.data.model.CitiesResultModel
import javax.inject.Inject

class SearchCitiesRepository @Inject
constructor(
    private val service: RepositoryService
) : BaseRepository(), IRepository {
    override suspend fun searchCities(query: String): ApiResponse<CitiesResultModel> =
        executeSafely(
            call =
            {
                service.searchCities(query)
            }
        )
}
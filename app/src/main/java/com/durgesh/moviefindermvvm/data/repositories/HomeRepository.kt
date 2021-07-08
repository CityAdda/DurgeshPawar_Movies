package com.durgesh.moviefindermvvm.data.repositories

import com.durgesh.moviefindermvvm.data.model.SearchResults
import com.durgesh.moviefindermvvm.data.network.ApiInterface
import com.durgesh.moviefindermvvm.data.network.SafeApiRequest

class HomeRepository(
    private val api: ApiInterface
) : SafeApiRequest() {

    suspend fun getMovies(
        searchTitle: String,
        apiKey: String
    ): SearchResults {
        return apiRequest { api.getSearchResultData(searchTitle, apiKey) }
    }

    suspend fun getAllMovies(
        apiKey: String
    ): SearchResults {
        return apiRequest { api.getMovieData(apiKey) }
    }

}
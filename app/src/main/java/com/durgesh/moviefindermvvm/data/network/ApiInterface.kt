package com.durgesh.moviefindermvvm.data.network

import com.durgesh.moviefindermvvm.data.model.SearchResults
import com.durgesh.moviefindermvvm.util.AppConstant.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiInterface {
    @GET("reviews/search.json?")
    suspend fun getSearchResultData(
        @Query(value = "query") searchTitle: String, @Query(value = "api-key") apiKey: String
    ): Response<SearchResults>


    @GET("reviews/all.json?")
    suspend fun getMovieData(
        @Query(value = "api-key") apiKey: String
    ): Response<SearchResults>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): ApiInterface {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
    }
}
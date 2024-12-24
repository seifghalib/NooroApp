package com.example.nooro.api

import com.example.nooro.BuildConfig
import com.example.nooro.data.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    companion object {
        const val BASE_URL = "http://api.weatherapi.com/v1/"
        const val OPEN_CLIENT_ID = BuildConfig.OPENWEATHER_API_KEY
    }

    @GET("current.json")
    suspend fun getWeather(
        @Query("q") q: String,
        @Query("key") key: String = OPEN_CLIENT_ID
    ): Response<ApiResponse>
}
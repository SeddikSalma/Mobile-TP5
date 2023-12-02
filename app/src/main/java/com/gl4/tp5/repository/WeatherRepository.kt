package com.gl4.tp5.repository

import com.gl4.tp5.fileFromJson.WeatherApiResponse
import com.gl4.tp5.network.WeatherAPI
import retrofit2.Call

class WeatherRepository : WeatherAPI {
    override fun getWeatherOnlyTunisia(): Call<WeatherApiResponse> {
        val response =  WeatherRetrofitHelper.retrofitService.getWeatherOnlyTunisia()
        println(response.request().url())
        return response
    }

    override fun getWeather(location:String,appId:String): Call<WeatherApiResponse> {
        val response =  WeatherRetrofitHelper.retrofitService.getWeather(location,appId)
        println(response.request().url())
        return response
    }

}
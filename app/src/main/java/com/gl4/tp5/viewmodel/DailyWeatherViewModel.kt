package com.gl4.tp5.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gl4.tp5.fileFromJson.DailyWeatherApiResponse
import com.gl4.tp5.fileFromJson.DataToShow
import com.gl4.tp5.repository.DetailsWeatherRepopository
import java.sql.Date

class DailyWeatherViewModel( private val dailyWeather: DetailsWeatherRepopository = DetailsWeatherRepopository()): ViewModel() {

    val weatherDailyData: MutableLiveData<Array<DataToShow>> by lazy {
        MutableLiveData<Array<DataToShow>>()
    }
    fun loadData(location: String,appId: String) {
        var data = dailyWeather.getWeather(location,appId)

        data.enqueue(object : retrofit2.Callback<DailyWeatherApiResponse> {
            override fun onResponse(
                call: retrofit2.Call<DailyWeatherApiResponse>,
                response: retrofit2.Response<DailyWeatherApiResponse>
            ) {
                if (response.isSuccessful) {
                    println("response is successful")
                    var weather = response.body()!!
                    var list = listOf<DataToShow>()
                    for(daily in weather.list){

                        var data = DataToShow()
                        data.date = Date(daily.dt.toLong()*1000).toString()
                        data.image=daily.weather[0].icon
                        data.humidity = daily.humidity
                        data.pressure = daily.pressure
                        data.description = daily.weather[0].description
                        data.temp = daily.temp.day
                        list+=data
                        }


                    weatherDailyData.value = list.toTypedArray()

                } else {
                   println(response.errorBody())
                }
            }

            override fun onFailure(call: retrofit2.Call<DailyWeatherApiResponse>, t: Throwable) {
               println(t.message.toString())
            }
        })

    }
}
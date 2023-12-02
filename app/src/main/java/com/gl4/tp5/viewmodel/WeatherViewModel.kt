package com.gl4.tp5.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gl4.tp5.fileFromJson.WeatherApiResponse
import com.gl4.tp5.fileFromJson.DataToShow
import com.gl4.tp5.repository.WeatherRepository
import java.sql.Date

//hetha kil observable bch yupdati ay 7keya (UI)
class WeatherViewModel( private val weatherRepository: WeatherRepository = WeatherRepository()
) : ViewModel() {

//data ki tetbadel tannonci lel 3bed lkol esta3malneha 5ater fama data bch tetbadel
   val dataToShow: MutableLiveData<DataToShow> by lazy {
        MutableLiveData<DataToShow>()
    }

//todo to delete 
    fun loadDataTN() {
        var data = weatherRepository.getWeatherOnlyTunisia()
        data.enqueue(object : retrofit2.Callback<WeatherApiResponse> {
            override fun onResponse(
                call: retrofit2.Call<WeatherApiResponse>,
                response: retrofit2.Response<WeatherApiResponse>
            ) {
                if (response.isSuccessful) {
                    var weather = response.body()!!
                    var tempData = DataToShow()
                    tempData.date = Date(weather.dt.toLong()*1000).toString()
                    tempData.image=weather.weather[0].icon
                    tempData.humidity = weather.main.humidity
                    tempData.pressure = weather.main.pressure
                    tempData.description = weather.weather[0].description
                    tempData.temp = weather.main.temp
                    dataToShow.value = tempData
                } else {
                    println(response.errorBody())
                }
            }

            override fun onFailure(call: retrofit2.Call<WeatherApiResponse>, t: Throwable) {
                println(t.message.toString())
            }
        })

    }

    fun loadData(location: String,appId: String) {
        var data = weatherRepository.getWeather(location,appId)
        //ta3mel appel async 
        data.enqueue(object : retrofit2.Callback<WeatherApiResponse> { 
            override fun onResponse(
                call: retrofit2.Call<WeatherApiResponse>,
                response: retrofit2.Response<WeatherApiResponse>
            ) {
                if (response.isSuccessful) {

                    var weather = response.body()!!
                    var tempData = DataToShow()

                    tempData.date = Date(weather.dt.toLong()*1000).toString()
                    tempData.image=weather.weather[0].icon
                    tempData.humidity = weather.main.humidity
                    tempData.pressure = weather.main.pressure
                    tempData.description = weather.weather[0].description
                    tempData.temp = weather.main.temp
                    dataToShow.value = tempData
                } else {
                     println(response.errorBody())
                }
            }

            override fun onFailure(call: retrofit2.Call<WeatherApiResponse>, t: Throwable) {
               println(t.message.toString())
            }
        })

    }

}
package com.gl4.tp5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gl4.tp5.adapters.DetailsAdapter
import com.gl4.tp5.databinding.DetailsBinding
import com.gl4.tp5.fileFromJson.DataToShow
import com.gl4.tp5.viewmodel.DailyWeatherViewModel

class DetailsActivity : AppCompatActivity() {


    private lateinit var binding: DetailsBinding
    private val dailyWeatherModel: DailyWeatherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var myAdapter = DetailsAdapter(emptyArray())

        val dailyWeatherObserver = Observer<Array<DataToShow>> { weather ->
            binding.textView.text = "Daily Forecast in "+ intent.getStringExtra("location").toString()
            myAdapter.setData(weather)
        }
        dailyWeatherModel.weatherDailyData.observe(this, dailyWeatherObserver)

        var location:String = intent.getStringExtra("location").toString()
        var appId:String = intent.getStringExtra("appId").toString()
        dailyWeatherModel.loadData(location!!,appId!!)
//houni bch te5dem el func fel 24

        val recycler = binding.recyclerView
        recycler.apply{
            adapter = myAdapter
            layoutManager = LinearLayoutManager(this@DetailsActivity)
        }

    }
}
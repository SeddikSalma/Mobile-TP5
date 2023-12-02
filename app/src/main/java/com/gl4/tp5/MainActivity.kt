package com.gl4.tp5

import android.R
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.gl4.tp5.databinding.ActivityMainBinding
import com.gl4.tp5.fileFromJson.DataToShow
import com.gl4.tp5.viewmodel.WeatherViewModel
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private val weatherViewModel: WeatherViewModel by viewModels()
    private lateinit var layoutBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        layoutBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(layoutBinding.root)
        layoutBinding.detailsButton.setBackgroundColor(Color.RED)
//kol ma ysir changement bch ye5dem hethi
        val weatherObserver = Observer<DataToShow> { weather ->
            println(weather)

            layoutBinding.temperature.text = weather.temp.toString()
            layoutBinding.description.text = weather.description
            layoutBinding.humidity.text =weather.humidity.toString()
            layoutBinding.pressure.text =weather.pressure.toString()
            try {
                val ims = assets.open(weather.image+"@2x.png")
                val d = Drawable.createFromStream(ims, null)
                layoutBinding.mImage.setImageDrawable(d)
            } catch (ex: IOException) {
                println(ex)
                return@Observer
            }
        }
        weatherViewModel.dataToShow.observe(this, weatherObserver)


        val countries = listOf("London", "Paris", "New York", "Tunis")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, countries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        layoutBinding.spinner.adapter = adapter

        layoutBinding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                weatherViewModel.loadData(countries[position],"17db59488cadcad345211c36304a9266")
                layoutBinding.country.text=countries[position];
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { // Another interface callback
            }
        }
        layoutBinding.detailsButton.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("location", layoutBinding.spinner.selectedItem.toString())
            intent.putExtra("appId", "17db59488cadcad345211c36304a9266")
            startActivity(intent)
        }
    }
}
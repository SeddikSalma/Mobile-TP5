package com.gl4.tp5.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gl4.tp5.R
import com.gl4.tp5.databinding.ItemBinding
import com.gl4.tp5.fileFromJson.DataToShow
import java.io.IOException

class DetailsAdapter(private val dataToShow: Array<DataToShow>): RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>() {
    var tempDataToShow: Array<DataToShow> = dataToShow

    private lateinit var binding: ItemBinding
    //tnoti el recycler view ken haja tbadlet
    fun setData(data: Array<DataToShow>) {
        tempDataToShow = data
        notifyDataSetChanged()
    }
    class DetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var date :TextView = view.findViewById(R.id.date_item)
        var temp:TextView = view.findViewById(R.id.temperature_item)
        var description:TextView = view.findViewById(R.id.description_item)
        var humidity:TextView = view.findViewById(R.id.humidity_item)
        var pressure:TextView = view.findViewById(R.id.pressure_item)
        var image:ImageView = view.findViewById(R.id.mImage_item)
    }
// managamch na3mel binding 5aterha mahech activity
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false) // Replace with your item layout
        return DetailsViewHolder(view)
    }

    override fun getItemCount(): Int {
        println("size is : "+tempDataToShow.size.toString())
        return tempDataToShow.size
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        holder.date.text = tempDataToShow[position].date
        holder.temp.text = tempDataToShow[position].temp.toString()
        holder.description.text = tempDataToShow[position].description
        holder.humidity.text = tempDataToShow[position].humidity.toString()
        holder.pressure.text = tempDataToShow[position].pressure.toString()
        try {

            val context = holder.itemView.context
            val assetManager = context.assets
            val ims = assetManager.open(tempDataToShow[position].image+"@2x.png")
            val d = Drawable.createFromStream(ims, null)
            holder.image.setImageDrawable(d)
        } catch (ex: IOException) {
            println(ex)
            return
        }

    }
}
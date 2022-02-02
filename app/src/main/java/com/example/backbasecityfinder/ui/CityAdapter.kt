package com.example.backbasecityfinder.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.backbasecityfinder.R
import com.example.backbasecityfinder.data.remote.dto.City
import com.example.backbasecityfinder.data.remote.dto.Coord

@Suppress("ComplexMethod")
class CityAdapter(
    private val onItemClickListener: ((Coord) -> Unit)? = null
) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {
    var citiesList = listOf<City>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.city_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return citiesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(citiesList[position], onItemClickListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cityTextView: TextView = itemView.findViewById(R.id.cityTextView)

        fun bindView(
            city: City,
            onItemClickListener: ((Coord) -> Unit)?
        ) {
            cityTextView.text = "${city.name}, ${city.country}"
            cityTextView.setOnClickListener {
                onItemClickListener?.invoke(city.coord)
            }
        }
    }
}

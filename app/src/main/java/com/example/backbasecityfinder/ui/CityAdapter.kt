package com.example.backbasecityfinder.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.backbasecityfinder.R
import com.example.backbasecityfinder.domain.model.CityDomainModel

@Suppress("ComplexMethod")
class CityAdapter(
    private val onItemClickListener: ((Double, Double) -> Unit)? = null
) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {
    var citiesList = listOf<CityDomainModel>()
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
        private val coordinatesTextView: TextView = itemView.findViewById(R.id.coordinatesTextView)

        fun bindView(
            city: CityDomainModel,
            onItemClickListener: ((Double, Double) -> Unit)?
        ) {
            cityTextView.text = city.title
            coordinatesTextView.text =
                "${city.latitude},${city.longitude}"
            cityTextView.setOnClickListener {
                onItemClickListener?.invoke(city.latitude, city.longitude)
            }
        }
    }
}

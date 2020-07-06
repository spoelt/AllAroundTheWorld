package com.spoelt.allaroundtheworld.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spoelt.allaroundtheworld.R
import com.spoelt.allaroundtheworld.data.Location
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item_location.view.*

class LocationListAdapter(private var context: Context, private val dataList: ArrayList<Location>) :
    RecyclerView.Adapter<LocationListAdapter.LocationViewHolder>() {
    private val list = ArrayList<Location>(dataList)

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_location, parent, false)
        return LocationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewLocation: ImageView = itemView.placeImage
        private val textViewLocationName: TextView = itemView.placeName

        fun bind(location: Location) {
            textViewLocationName.text = location.name
            /*Picasso.get()
                .load(location.imagePath)
                .error(R.drawable.ic_broken_image_gray_24dp)
                .into(imageViewLocation)*/
        }
    }
}
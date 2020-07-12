package com.spoelt.allaroundtheworld.ui.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spoelt.allaroundtheworld.R
import com.spoelt.allaroundtheworld.data.model.Location
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_item_location.view.*
import java.io.File
import java.net.URI

class LocationListAdapter(private var context: Context, private val dataList: ArrayList<Location>) :
    RecyclerView.Adapter<LocationListAdapter.LocationViewHolder>() {
    lateinit var itemClickListener: OnItemClickListener
    private var list: List<Location> = ArrayList(dataList)

    fun getLocationAtPosition(position: Int) = list.get(position)

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_location, parent, false)
        return LocationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.placeHolder.setOnClickListener(this)
        }

        private val imageViewLocation: ImageView = itemView.placeImage
        private val textViewLocationName: TextView = itemView.placeName

        fun bind(location: Location) {
            textViewLocationName.text = location.name
            Picasso.get()
                .load(Uri.parse(location.imagePath!!))
                .error(R.drawable.ic_broken_image_gray_24dp)
                .into(imageViewLocation)
        }

        override fun onClick(view: View) = itemClickListener.onItemClick(itemView, adapterPosition)
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun updateList(updatedList: List<Location>) {
        list = updatedList
        notifyItemRangeChanged(0, itemCount)
    }
}
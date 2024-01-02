package com.nlabs.trakingapp.fragments.list

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.nlabs.trakingapp.location_data.InstantLocation
import com.nlabs.trakingapp.databinding.InstantLocationItemBinding

class ListAdapter: RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var dataset = emptyList<InstantLocation>()

    class ViewHolder(val binding: InstantLocationItemBinding): RecyclerView.ViewHolder(binding.root) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(InstantLocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataset[position]
        // Binding location data to card item
        with(holder){
            binding.nameTextView.text = currentItem.id.toString()
            binding.latitudeTextView.text = currentItem.latitude.toString()
            binding.longitudeTextView.text = currentItem.longitude.toString()
            binding.altitudeTextView.text = currentItem.altitude.toString()
        }
    }

    /**
     * Define passed argument as RecycleView dataset
     * */
    fun setDataset(locationList: List<InstantLocation>){
        this.dataset = locationList
        notifyDataSetChanged()
    }

}
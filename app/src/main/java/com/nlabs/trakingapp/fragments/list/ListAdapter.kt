package com.nlabs.trakingapp.fragments.list

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.nlabs.trakingapp.location_data.InstantLocation
import com.nlabs.trakingapp.databinding.InstantLocationItemBinding
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ListAdapter: RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    /**
     * Collection with objects to construct recycleView
     */
    private var dataset = emptyList<InstantLocation>()

    class ViewHolder(val binding: InstantLocationItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(InstantLocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataset[position].let {
            // Binding location data to card item
            with(holder){
                val numFormat = "%.6f"
                binding.nameTextView.text = it.id.toString()
                binding.latitudeTextView.text = String.format(numFormat, it.latitude)
                binding.longitudeTextView.text = String.format(numFormat, it.longitude)
                binding.altitudeTextView.text = String.format(numFormat, it.altitude)
                binding.creationInstantTextView.text = DateTimeFormatter.ofPattern("dd/MM/yy | HH:mm").format(
                    LocalDateTime.ofInstant(it.creationInstant, ZoneId.systemDefault()))
            }
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
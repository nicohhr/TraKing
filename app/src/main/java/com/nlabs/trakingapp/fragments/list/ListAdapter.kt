package com.nlabs.trakingapp.fragments.list

import android.view.*
import android.view.View.OnClickListener
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
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
    private lateinit var onItemClickListener: OnItemClickListener

    inner class ViewHolder(val binding: InstantLocationItemBinding): RecyclerView.ViewHolder(binding.root), OnMapReadyCallback, OnClickListener {

        private lateinit var map: GoogleMap
        lateinit var latLng: LatLng

        init {
            // Binding map instance
            with(binding.mapView){
                // Initialise the MapView
                onCreate(null)
                // Set the map ready callback  to receive the GoogleMap object
                getMapAsync(this@ViewHolder)
            }

            // Binding cardView click listener
            binding.cardView.setOnClickListener(this@ViewHolder)

            // Binding swipe to delete action

        }

        fun setMapLocation() {
            if(!::map.isInitialized) return
            with(map){
                moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f))
                addMarker(MarkerOptions().position(latLng))
                mapType = GoogleMap.MAP_TYPE_NORMAL
            }
        }

        fun clearView() {
            with(map){
                clear()
                mapType = GoogleMap.MAP_TYPE_NONE
            }
        }

        override fun onMapReady(googleMap: GoogleMap) {
            MapsInitializer.initialize(binding.root.context)
            // If map is not initialised properly
            map = googleMap
            setMapLocation()
        }

        override fun onClick(v: View?) {
            val pos = adapterPosition
            //  Take action only if the position exist in the list
            if (pos != RecyclerView.NO_POSITION){
                onItemClickListener.onItemClick(dataset[pos])
                notifyItemChanged(pos)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(InstantLocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataset[position].let {
            // Binding location data to card item
            with(holder){
                val numFormat = "%.6f"
                latLng = it.getLatLng()
                // Set item elements
                binding.nameTextView.text = it.id.toString()
                binding.latitudeTextView.text = String.format(numFormat, it.latitude)
                binding.longitudeTextView.text = String.format(numFormat, it.longitude)
                binding.altitudeTextView.text = String.format(numFormat, it.altitude)
                binding.creationInstantTextView.text = DateTimeFormatter.ofPattern("dd/MM/yy | HH:mm").format(
                    LocalDateTime.ofInstant(it.creationInstant, ZoneId.systemDefault()))
                setMapLocation()
            }
        }
    }

    fun setListeners(clickListener: OnItemClickListener){
        this.onItemClickListener = clickListener
    }

    fun getListInstance(pos: Int): InstantLocation{
        return dataset[pos]
    }

    /**
     * Define passed argument as RecycleView dataset
     * */
    fun setDataset(locationList: List<InstantLocation>){
        this.dataset = locationList
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(currentLocation: InstantLocation)
    }
}
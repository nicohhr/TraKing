package com.nlabs.trakingapp.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nlabs.trakingapp.databinding.FragmentLocationListBinding
import com.nlabs.trakingapp.location_data.InstantLocation
import com.nlabs.trakingapp.location_data.InstantLocationViewModel

class FragmentLocationList : Fragment(), ListAdapter.OnItemClickListener {

    // Variables
    private lateinit var mInstantLocationViewModel: InstantLocationViewModel
    private var fragmentLocationListBinding: FragmentLocationListBinding? = null
    private val recyclerListener = RecyclerView.RecyclerListener { holder ->
        val mapHolder = holder as ListAdapter.ViewHolder
        mapHolder.clearView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Initializing RecycleView
        fragmentLocationListBinding = FragmentLocationListBinding.inflate(inflater, container, false)
        val listAdapter = ListAdapter()
        listAdapter.setOnItemClickListener(this)
        fragmentLocationListBinding!!.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FragmentLocationList.context)
            adapter = listAdapter
            setRecyclerListener(recyclerListener)
        }

        // Initializing ViewModel
        mInstantLocationViewModel =
            ViewModelProvider(this)[InstantLocationViewModel::class.java]
        mInstantLocationViewModel.getData.observe(viewLifecycleOwner) { location ->
            listAdapter.setDataset(location)
        }

        return fragmentLocationListBinding!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentLocationListBinding = null
    }

    /**
     * Defines action to be taken when
     * an item is selected in the recycle view.
     */
    override fun onItemClick(currentLocation: InstantLocation) {
        mInstantLocationViewModel.deleteLocation(currentLocation)
        Toast.makeText(requireContext(), "Deleting ${currentLocation.id}", Toast.LENGTH_SHORT).show()
    }
}
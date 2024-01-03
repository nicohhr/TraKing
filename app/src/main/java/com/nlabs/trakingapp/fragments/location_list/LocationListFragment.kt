package com.nlabs.trakingapp.fragments.location_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nlabs.trakingapp.databinding.FragmentLocationListBinding
import com.nlabs.trakingapp.data.location.InstantLocation
import com.nlabs.trakingapp.data.location.InstantLocationViewModel

class LocationListFragment : Fragment(), LocationListAdapter.OnItemClickListener {

    // Variables
    private lateinit var mInstantLocationViewModel: InstantLocationViewModel
    private lateinit var listAdapter: LocationListAdapter
    private var fragmentLocationListBinding: FragmentLocationListBinding? = null
    private val recyclerListener = RecyclerView.RecyclerListener { holder ->
        val mapHolder = holder as LocationListAdapter.ViewHolder
        mapHolder.clearView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Initializing RecycleView
        fragmentLocationListBinding = FragmentLocationListBinding.inflate(inflater, container, false)
        listAdapter = LocationListAdapter()
        listAdapter.setListeners(this)
        fragmentLocationListBinding!!.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@LocationListFragment.context)
            adapter = listAdapter
            setRecyclerListener(recyclerListener)
        }

        // Initializing ViewModel
        mInstantLocationViewModel =
            ViewModelProvider(this)[InstantLocationViewModel::class.java]
        mInstantLocationViewModel.getData.observe(viewLifecycleOwner) { location ->
            listAdapter.setDataset(location)
        }

        // Initializing Swipe to Delete Action
        swipeToDelete()

        return fragmentLocationListBinding!!.root
    }

    private fun swipeToDelete(){
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val currentLocation = listAdapter.getListInstance(viewHolder.adapterPosition)
                mInstantLocationViewModel.deleteLocation(currentLocation)
                Toast.makeText(requireContext(), "Deleting ${currentLocation.getShortKey()}", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(fragmentLocationListBinding?.recyclerView)
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
        //mInstantLocationViewModel.deleteLocation(currentLocation)
        //Toast.makeText(requireContext(), "Deleting ${currentLocation.id}", Toast.LENGTH_SHORT).show()
    }
}
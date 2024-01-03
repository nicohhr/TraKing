package com.nlabs.trakingapp.fragments.list

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
import com.nlabs.trakingapp.data.base_location.BaseLocation
import com.nlabs.trakingapp.data.base_location.BaseLocationViewModel

class FragmentLocationList : Fragment(), ListAdapter.OnItemClickListener {

    // Variables
    private lateinit var mBaseLocationViewModel: BaseLocationViewModel
    private lateinit var listAdapter: ListAdapter
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
        listAdapter = ListAdapter()
        listAdapter.setListeners(this)
        fragmentLocationListBinding!!.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FragmentLocationList.context)
            adapter = listAdapter
            setRecyclerListener(recyclerListener)
        }

        // Initializing ViewModel
        mBaseLocationViewModel =
            ViewModelProvider(this)[BaseLocationViewModel::class.java]
        mBaseLocationViewModel.getData.observe(viewLifecycleOwner) { location ->
            listAdapter.setDataset(location)
        }

        // Initializing Swipe to Delete Action
        swipeToDelete()

        return fragmentLocationListBinding!!.root
    }

    private fun swipeToDelete(){
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val currentLocation = listAdapter.getListInstance(viewHolder.adapterPosition)
                mBaseLocationViewModel.deleteLocation(currentLocation)
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
    override fun onItemClick(currentLocation: BaseLocation) {
        //mInstantLocationViewModel.deleteLocation(currentLocation)
        //Toast.makeText(requireContext(), "Deleting ${currentLocation.id}", Toast.LENGTH_SHORT).show()
    }
}
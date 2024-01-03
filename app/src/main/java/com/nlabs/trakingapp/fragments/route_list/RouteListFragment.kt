package com.nlabs.trakingapp.fragments.route_list

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
import com.nlabs.trakingapp.R
import com.nlabs.trakingapp.data.route.RouteViewModel
import com.nlabs.trakingapp.databinding.FragmentRouteListBinding

class RouteListFragment : Fragment() {

    // Variables
    private lateinit var mRouteViewModel: RouteViewModel
    private lateinit var listAdapter: RouteListAdapter
    private var viewBinding: FragmentRouteListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Initializing RecycleView
        viewBinding = FragmentRouteListBinding.inflate(inflater, container, false)
        listAdapter = RouteListAdapter()
        viewBinding!!.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@RouteListFragment.context)
            adapter = listAdapter
        }

        // Initializing viewModel
        mRouteViewModel =
                ViewModelProvider(this)[RouteViewModel::class.java]
        mRouteViewModel.getData.observe(viewLifecycleOwner) { routes ->
            listAdapter.setDataset(routes)
        }

        // Initializing Swipe to Delete Action
        swipeToDelete()

        // Inflate the layout for this fragment
        return viewBinding!!.root
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
                val currentRoute = listAdapter.getListInstance(viewHolder.adapterPosition)
                mRouteViewModel.deleteRoute(currentRoute)
                Toast.makeText(requireContext(), "Deleting ${currentRoute.getShortKey()}", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(viewBinding?.recyclerView)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}
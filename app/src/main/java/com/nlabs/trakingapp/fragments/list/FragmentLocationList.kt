package com.nlabs.trakingapp.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nlabs.trakingapp.databinding.FragmentLocationListBinding
import com.nlabs.trakingapp.location_data.InstantLocationViewModel

class FragmentLocationList : Fragment() {

    // Variables
    private lateinit var mInstantLocationViewModel: InstantLocationViewModel
    private var fragmentLocationListBinding: FragmentLocationListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Initializing RecycleView
        fragmentLocationListBinding = FragmentLocationListBinding.inflate(inflater, container, false)
        val listAdapter = ListAdapter()
        fragmentLocationListBinding!!.recyclerView.adapter = listAdapter
        fragmentLocationListBinding!!.recyclerView.layoutManager = LinearLayoutManager(this@FragmentLocationList.context)

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
}
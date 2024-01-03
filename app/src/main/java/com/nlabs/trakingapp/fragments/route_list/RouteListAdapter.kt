package com.nlabs.trakingapp.fragments.route_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nlabs.trakingapp.data.route.Route
import com.nlabs.trakingapp.databinding.RouteItemBinding

class RouteListAdapter: RecyclerView.Adapter<RouteListAdapter.ViewHolder>() {

    /**
     * Collection with objects to construct recycleView
     */
    private var dataset = emptyList<Route>()

    class ViewHolder(val binding: RouteItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RouteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataset[position].let{
            // Binding location data to card item
            with(holder){
                binding.nameTextView.text = it.getName()
                binding.startTimeTextView.text = it.startTimeToString()

                if (it.stopTime == 0L){
                    binding.stopTimeTextView.text = "---"
                } else {
                    binding.stopTimeTextView.text = it.stopTimeToString()
                }

                if (it.isRecording()) {
                    binding.isRecordingTextView.text = "TraKing Location..."
                }
                else {
                    binding.isRecordingTextView.text = "Finished."
                }
            }
        }
    }

    fun getListInstance(pos: Int): Route {
        return dataset[pos]
    }

    fun setDataset(routeList: List<Route>){
        this.dataset = routeList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dataset.size
}
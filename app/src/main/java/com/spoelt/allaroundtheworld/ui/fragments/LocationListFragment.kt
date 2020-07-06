package com.spoelt.allaroundtheworld.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.spoelt.allaroundtheworld.R
import com.spoelt.allaroundtheworld.data.Location
import com.spoelt.allaroundtheworld.databinding.FragmentLocationListBinding
import com.spoelt.allaroundtheworld.ui.adapter.LocationListAdapter
import java.util.*
import kotlin.collections.ArrayList

class LocationListFragment : Fragment() {
    private lateinit var locationAdapter: LocationListAdapter
    private lateinit var binding: FragmentLocationListBinding
    private lateinit var staggeredLayoutManager: StaggeredGridLayoutManager
    private val locationList: ArrayList<Location> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_location_list, container, false)

        binding.lifecycleOwner = this

        // set up locationList in viewModel
        locationList.addAll(
            listOf(Location("Vienna", "Fun Day in VIE", null, null),
                Location("Berlin", "BERLIN CALLING", null, null),
                Location("Stockholm", "Jag alskar dig", null, null))
        )

        setUpRecyclerView()

        return binding.root
    }

    private fun setUpRecyclerView() {
        locationAdapter = LocationListAdapter(requireContext(), locationList)
        binding.recyclerViewLocations.adapter = locationAdapter

        staggeredLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerViewLocations.layoutManager = staggeredLayoutManager
    }
}

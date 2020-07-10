package com.spoelt.allaroundtheworld.ui.fragments


import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.spoelt.allaroundtheworld.R
import com.spoelt.allaroundtheworld.data.Location
import com.spoelt.allaroundtheworld.databinding.FragmentLocationListBinding
import com.spoelt.allaroundtheworld.ui.adapter.LocationListAdapter
import com.spoelt.allaroundtheworld.ui.viewModel.LocationListViewModel
import java.util.*
import kotlin.collections.ArrayList

class LocationListFragment : Fragment() {
    private lateinit var locationAdapter: LocationListAdapter
    private lateinit var binding: FragmentLocationListBinding
    private lateinit var staggeredLayoutManager: StaggeredGridLayoutManager
    private lateinit var viewModel: LocationListViewModel
    private val locationList: ArrayList<Location> = ArrayList()

    private val onItemClickListener = object : LocationListAdapter.OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            val action = LocationListFragmentDirections.actionLocationListFragmentToLocationDetailFragment()
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_location_list, container, false)

        binding.lifecycleOwner = this

        setUpViewModel()
        setUpRecyclerView()
        setUpClickListener()

        return binding.root
    }

    private fun setUpClickListener() {
        binding.fabAddLocation.setOnClickListener {
            // start new fragment
        }
    }

    private fun setUpRecyclerView() {
        locationAdapter = LocationListAdapter(requireContext(), locationList)
        locationAdapter.setOnItemClickListener(onItemClickListener)
        binding.recyclerViewLocations.adapter = locationAdapter

        staggeredLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerViewLocations.layoutManager = staggeredLayoutManager
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this).get(LocationListViewModel::class.java)
        binding.viewModel = viewModel
    }
}

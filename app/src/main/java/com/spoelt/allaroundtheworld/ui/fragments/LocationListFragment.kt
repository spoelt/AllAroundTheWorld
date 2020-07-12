package com.spoelt.allaroundtheworld.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.spoelt.allaroundtheworld.R
import com.spoelt.allaroundtheworld.data.db.DatabaseBuilder
import com.spoelt.allaroundtheworld.data.db.DatabaseHelperImpl
import com.spoelt.allaroundtheworld.data.model.Location
import com.spoelt.allaroundtheworld.databinding.FragmentLocationListBinding
import com.spoelt.allaroundtheworld.ui.adapter.LocationListAdapter
import com.spoelt.allaroundtheworld.ui.viewModel.LocationListViewModel
import com.spoelt.allaroundtheworld.ui.viewModel.ViewModelFactory


class LocationListFragment : Fragment() {
    private lateinit var locationAdapter: LocationListAdapter
    private lateinit var binding: FragmentLocationListBinding
    private lateinit var staggeredLayoutManager: StaggeredGridLayoutManager
    private lateinit var viewModel: LocationListViewModel
    private val locationList: ArrayList<Location> = ArrayList()

    private val onItemClickListener = object : LocationListAdapter.OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            /*val action = LocationListFragmentDirections.actionLocationListFragmentToLocationDetailFragment()
            findNavController().navigate(action)*/
            // tbd
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
        setUpItemTouchHelper()
        setUpOnClickListener()
        setUpObserver()

        return binding.root
    }

    private fun setUpObserver() {
        viewModel.locationList.observe(viewLifecycleOwner, Observer {
            it.let {
                locationAdapter.updateList(it)
            }
        })
    }

    private fun setUpOnClickListener() {
        binding.fabAddLocation.setOnClickListener {
            it.findNavController().navigate(R.id.createLocationFragment)
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
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                DatabaseHelperImpl(
                    DatabaseBuilder.getInstance(requireContext())
                )
            )
        ).get(LocationListViewModel::class.java)
        binding.viewModel = viewModel
    }

    private fun setUpItemTouchHelper() {
        val helper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
                ) {
                    val position = viewHolder.adapterPosition
                    val locationToDelete: Location = locationAdapter.getLocationAtPosition(position)
                    viewModel.deleteLocation(locationToDelete)
                    locationAdapter.notifyDataSetChanged()
                }
            })

        helper.attachToRecyclerView(binding.recyclerViewLocations)
    }
}

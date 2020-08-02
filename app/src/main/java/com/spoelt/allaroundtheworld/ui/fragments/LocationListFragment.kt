package com.spoelt.allaroundtheworld.ui.fragments


import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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
import kotlin.math.roundToInt


class LocationListFragment : Fragment() {
    private lateinit var locationAdapter: LocationListAdapter
    private lateinit var binding: FragmentLocationListBinding
    private lateinit var staggeredLayoutManager: StaggeredGridLayoutManager
    private lateinit var viewModel: LocationListViewModel

    private val onItemClickListener = object : LocationListAdapter.OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            val detailLocation = locationAdapter.getLocationAtPosition(position)
            val action =
                LocationListFragmentDirections.actionLocationListFragmentToLocationDetailFragment(
                    detailLocation
                )
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
        setUpItemTouchHelper()
        setUpOnClickListener()
        setUpObservers()

        return binding.root
    }

    private fun setUpObservers() {
        viewModel.locationList.observe(viewLifecycleOwner, Observer {
            it?.let {
                locationAdapter.updateList(it)
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.isNotEmpty()) {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setUpOnClickListener() {
        binding.fabAddLocation.setOnClickListener {
            it.findNavController()
                .navigate(LocationListFragmentDirections.actionLocationListFragmentToCreateLocationFragment())
        }
    }

    private fun setUpRecyclerView() {
        locationAdapter = LocationListAdapter()
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
        val trashIcon = resources.getDrawable(R.drawable.ic_delete_white_48dp)
        val iconMarginTopBottom =
            resources.getDimension(R.dimen.icon_margin_top_bottom).roundToInt()
        val iconMarginLeftRight =
            resources.getDimension(R.dimen.icon_margin_left_right).roundToInt()

        val helper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.RIGHT
            ) {
                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    c.clipRect(
                        0f, viewHolder.itemView.top.toFloat(),
                        dX, viewHolder.itemView.bottom.toFloat()
                    )

                    if (dX < c.width / 3)
                        c.drawColor(Color.GRAY)
                    else
                        c.drawColor(Color.RED)

                    trashIcon.bounds = Rect(
                        iconMarginLeftRight,
                        viewHolder.itemView.top + iconMarginTopBottom,
                        iconMarginLeftRight + trashIcon.intrinsicWidth,
                        viewHolder.itemView.top + trashIcon.intrinsicHeight
                                + iconMarginTopBottom
                    )

                    trashIcon.draw(c)

                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }

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

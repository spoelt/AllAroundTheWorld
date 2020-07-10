package com.spoelt.allaroundtheworld.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.spoelt.allaroundtheworld.R
import com.spoelt.allaroundtheworld.databinding.FragmentLocationDetailBinding
import com.spoelt.allaroundtheworld.databinding.FragmentLocationListBinding

class LocationDetailFragment : Fragment() {
    private lateinit var binding: FragmentLocationDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_location_detail, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = LocationDetailFragmentArgs.fromBundle(requireArguments())
        if (args.location != null) {
            // display location details
        }
    }
}

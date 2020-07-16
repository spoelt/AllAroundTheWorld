package com.spoelt.allaroundtheworld.ui.fragments


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.github.florent37.picassopalette.PicassoPalette
import com.spoelt.allaroundtheworld.R
import com.spoelt.allaroundtheworld.data.db.DatabaseBuilder
import com.spoelt.allaroundtheworld.data.db.DatabaseHelperImpl
import com.spoelt.allaroundtheworld.databinding.FragmentLocationDetailBinding
import com.spoelt.allaroundtheworld.ui.viewModel.LocationDetailViewModel
import com.spoelt.allaroundtheworld.ui.viewModel.ViewModelFactory
import com.squareup.picasso.Picasso

class LocationDetailFragment : Fragment() {
    private lateinit var binding: FragmentLocationDetailBinding
    private lateinit var viewModel: LocationDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_location_detail, container, false)
        binding.lifecycleOwner = this

        setUpViewModel()

        return binding.root
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                DatabaseHelperImpl(
                    DatabaseBuilder.getInstance(requireContext())
                )
            )
        ).get(LocationDetailViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = LocationDetailFragmentArgs.fromBundle(requireArguments())
        Picasso.get()
            .load(Uri.parse(args.location.imagePath!!))
            .error(R.drawable.ic_broken_image_gray_24dp)
            .into(
                binding.placeImage, PicassoPalette.with(args.location.imagePath, binding.placeImage)
                    .use(PicassoPalette.Profile.VIBRANT)
                    .intoBackground(binding.placeNameHolder, PicassoPalette.Swatch.RGB)
            )
        binding.placeName.text = args.location.name
        binding.caption.text = args.location.caption
    }
}

package com.spoelt.allaroundtheworld.ui.fragments


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.github.florent37.picassopalette.PicassoPalette
import com.spoelt.allaroundtheworld.R
import com.spoelt.allaroundtheworld.data.db.DatabaseBuilder
import com.spoelt.allaroundtheworld.data.db.DatabaseHelperImpl
import com.spoelt.allaroundtheworld.data.model.Location
import com.spoelt.allaroundtheworld.databinding.FragmentLocationDetailBinding
import com.spoelt.allaroundtheworld.ui.viewModel.LocationDetailViewModel
import com.spoelt.allaroundtheworld.ui.viewModel.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class LocationDetailFragment : Fragment() {
    private lateinit var binding: FragmentLocationDetailBinding
    private lateinit var viewModel: LocationDetailViewModel
    private lateinit var args: LocationDetailFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_location_detail, container, false)
        binding.lifecycleOwner = this

        setUpActionBar()
        setUpViewModel()
        setUpObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args = LocationDetailFragmentArgs.fromBundle(requireArguments())
        loadData(args)
    }

    private fun setUpObservers() {
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.successMessageId.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(requireContext(), resources.getString(it), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setUpActionBar() {
        activity?.toolbar?.inflateMenu(R.menu.menu)
        activity?.toolbar?.setOnMenuItemClickListener {
            if (it.itemId == R.id.save) {
                val location = Location(
                    args.location.id,
                    args.location.name,
                    binding.textInputCaption.text.toString(),
                    args.location.imagePath
                )
                viewModel.updateLocation(location)
                findNavController().navigate(R.id.locationListFragment)
            }
            true
        }
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

    private fun loadData(args: LocationDetailFragmentArgs) {
        Picasso.get()
            .load(Uri.parse(args.location.imagePath!!))
            .error(R.drawable.ic_broken_image_gray_24dp)
            .into(
                binding.placeImage, PicassoPalette.with(args.location.imagePath, binding.placeImage)
                    .use(PicassoPalette.Profile.VIBRANT)
                    .intoBackground(binding.placeNameHolder, PicassoPalette.Swatch.RGB)
            )
        binding.placeName.text = args.location.name
        binding.textInputCaption.setText(args.location.caption)
    }
}

package com.spoelt.allaroundtheworld.ui.fragments


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.spoelt.allaroundtheworld.R
import com.spoelt.allaroundtheworld.data.db.DatabaseBuilder
import com.spoelt.allaroundtheworld.data.db.DatabaseHelperImpl
import com.spoelt.allaroundtheworld.databinding.FragmentCreateLocationBinding
import com.spoelt.allaroundtheworld.ui.viewModel.CreateLocationViewModel
import com.spoelt.allaroundtheworld.ui.viewModel.ViewModelFactory
import com.squareup.picasso.Picasso

const val PICK_FILE_RESULT_CODE = 1
const val MIME_TYPE_IMAGE = "image/jpeg"

class CreateLocationFragment : Fragment() {
    private lateinit var binding: FragmentCreateLocationBinding
    private lateinit var viewModel: CreateLocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_location, container, false)
        binding.lifecycleOwner = this

        setUpViewModel()
        binding.saveLocationButton.setOnClickListener {
            viewModel.saveLocation()
        }
        choosePicture()

        return binding.root
    }

    private fun choosePicture() {
        var chooseFile = Intent(Intent.ACTION_GET_CONTENT)
        chooseFile.type = MIME_TYPE_IMAGE
        chooseFile = Intent.createChooser(chooseFile, resources.getString(R.string.choose_file))
        startActivityForResult(chooseFile, PICK_FILE_RESULT_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            PICK_FILE_RESULT_CODE -> if (resultCode == -1) {
                data!!.data?.let { returnUri ->
                    loadPicture(returnUri)
                    binding.constraintLayout.visibility = View.VISIBLE
                    viewModel.imagePath.value = returnUri
                }
            }
        }
    }

    private fun loadPicture(uri: Uri) {
        Picasso.get()
            .load(uri)
            .error(R.drawable.ic_broken_image_gray_24dp)
            .into(binding.placeImage)
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                DatabaseHelperImpl(
                    DatabaseBuilder.getInstance(requireContext())
                )
            )
        ).get(CreateLocationViewModel::class.java)
        binding.viewModel = viewModel
    }
}

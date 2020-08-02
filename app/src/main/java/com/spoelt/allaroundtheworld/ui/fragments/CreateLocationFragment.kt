package com.spoelt.allaroundtheworld.ui.fragments


import android.content.Intent
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
import androidx.navigation.findNavController
import com.spoelt.allaroundtheworld.R
import com.spoelt.allaroundtheworld.data.db.DatabaseBuilder
import com.spoelt.allaroundtheworld.data.db.DatabaseHelperImpl
import com.spoelt.allaroundtheworld.databinding.FragmentCreateLocationBinding
import com.spoelt.allaroundtheworld.ui.viewModel.CreateLocationViewModel
import com.spoelt.allaroundtheworld.ui.viewModel.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*


const val PICK_FILE_RESULT_CODE = 1
const val FILE_PICKED = -1
const val REQUEST_CANCELLED = 0
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

        choosePicture()
        setUpActionBar()
        setUpViewModel()
        setUpObserver()

        return binding.root
    }

    private fun setUpActionBar() {
        this.activity?.toolbar?.inflateMenu(R.menu.menu)
        this.activity?.toolbar?.setOnMenuItemClickListener {
            if (it.itemId == R.id.save) {
                if (binding.textInputLocation.text.isNullOrBlank()) {
                    binding.textInputLocation.error = resources.getString(R.string.empty_location)
                } else {
                    viewModel.saveLocation()
                    view?.findNavController()?.navigate(R.id.locationListFragment)
                }
            }
            true
        }
    }

    private fun setUpObserver() {
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.isNotEmpty()) {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun choosePicture() {
        var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
        chooseFile.type = MIME_TYPE_IMAGE
        chooseFile.flags = (Intent.FLAG_GRANT_READ_URI_PERMISSION
                or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                or Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        chooseFile = Intent.createChooser(chooseFile, resources.getString(R.string.choose_file))
        startActivityForResult(chooseFile, PICK_FILE_RESULT_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_FILE_RESULT_CODE) {
            when (resultCode) {
                FILE_PICKED -> data!!.data?.let { returnUri ->
                    context?.contentResolver?.takePersistableUriPermission(
                        returnUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    loadPicture(returnUri)
                    binding.constraintLayout.visibility = View.VISIBLE
                    viewModel.imagePath.value = returnUri
                }
                REQUEST_CANCELLED -> view?.findNavController()?.navigate(R.id.locationListFragment)
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

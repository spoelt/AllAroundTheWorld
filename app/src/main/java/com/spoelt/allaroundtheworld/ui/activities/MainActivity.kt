package com.spoelt.allaroundtheworld.ui.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.spoelt.allaroundtheworld.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpToolbar()
    }

    private fun setUpToolbar() {
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.createLocationFragment -> {
                    toolbar.navigationIcon =
                        getDrawable(R.drawable.ic_cancel_white_24dp)
                    toolbar.title = getString(R.string.add_location_header)
                }
                R.id.locationDetailFragment -> {
                    toolbar.navigationIcon =
                        getDrawable(R.drawable.ic_arrow_back_white_24dp)
                    toolbar.title = getString(R.string.details_header)
                }
                R.id.locationListFragment -> {
                    toolbar.navigationIcon = null
                    toolbar.title = getString(R.string.app_name)
                    toolbar.menu?.clear()
                    hideKeyboard(this, this.currentFocus)
                }
            }
        }
    }

    private fun hideKeyboard(context: Context?, view: View?) {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}

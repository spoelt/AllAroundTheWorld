package com.spoelt.allaroundtheworld.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.spoelt.allaroundtheworld.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onSupportNavigateUp() = Navigation.findNavController(
        this,
        R.id.nav_host_fragment
    ).navigateUp()

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
                    toolbar.menu?.clear()
                }
                R.id.locationListFragment -> {
                    toolbar.navigationIcon = null
                    toolbar.title = getString(R.string.app_name)
                    toolbar.menu?.clear()
                }
            }
        }
    }
}

package com.spoelt.allaroundtheworld.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
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

        nav_host_fragment.findNavController()
    }
}

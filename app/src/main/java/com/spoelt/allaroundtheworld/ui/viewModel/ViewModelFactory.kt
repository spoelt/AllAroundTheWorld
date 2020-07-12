package com.spoelt.allaroundtheworld.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spoelt.allaroundtheworld.data.db.DatabaseHelper

class ViewModelFactory(private val dbHelper: DatabaseHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateLocationViewModel::class.java)) {
            return CreateLocationViewModel(dbHelper) as T
        }
        if (modelClass.isAssignableFrom(LocationListViewModel::class.java)) {
            return LocationListViewModel(dbHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
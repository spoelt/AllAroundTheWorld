package com.spoelt.allaroundtheworld.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoelt.allaroundtheworld.data.db.DatabaseHelper
import com.spoelt.allaroundtheworld.data.model.Location
import kotlinx.coroutines.launch

class LocationDetailViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {
    var errorMessage: MutableLiveData<String> = MutableLiveData()

    fun updateLocation(location: Location) {
        viewModelScope.launch {
            try {
                dbHelper.update(location)
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }
}
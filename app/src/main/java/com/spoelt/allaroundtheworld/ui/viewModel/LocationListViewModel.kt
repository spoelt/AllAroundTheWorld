package com.spoelt.allaroundtheworld.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoelt.allaroundtheworld.data.db.DatabaseHelper
import com.spoelt.allaroundtheworld.data.model.Location
import kotlinx.coroutines.launch

class LocationListViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {
    private val _locationList = MutableLiveData<MutableList<Location>>()
    val locationList: LiveData<MutableList<Location>>
        get() = _locationList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    init {
        getLocations()
    }

    private fun getLocations() {
        viewModelScope.launch {
            try {
                val locationsDb = dbHelper.getLocations() as ArrayList<Location>
                _locationList.value = locationsDb.asReversed()
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun deleteLocation(location: Location) {
        viewModelScope.launch {
            try {
                dbHelper.delete(location)
                _locationList.value?.remove(location)
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}
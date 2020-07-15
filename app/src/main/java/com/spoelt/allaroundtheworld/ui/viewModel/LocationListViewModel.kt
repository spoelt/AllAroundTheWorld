package com.spoelt.allaroundtheworld.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoelt.allaroundtheworld.data.db.DatabaseHelper
import com.spoelt.allaroundtheworld.data.model.Location
import kotlinx.coroutines.launch

class LocationListViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {
    var locationList: MutableLiveData<MutableList<Location>> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()

    init {
        getLocations()
    }

    private fun getLocations() {
        viewModelScope.launch {
            try {
                val locationsDb = dbHelper.getLocations() as ArrayList<Location>
                locationList.value = locationsDb.asReversed()
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }

    fun deleteLocation(location: Location) {
        viewModelScope.launch {
            try {
                dbHelper.delete(location)
                locationList.value?.remove(location)
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }
}
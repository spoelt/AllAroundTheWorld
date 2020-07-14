package com.spoelt.allaroundtheworld.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoelt.allaroundtheworld.data.db.DatabaseHelper
import com.spoelt.allaroundtheworld.data.model.Location
import kotlinx.coroutines.launch
import java.lang.Exception

class LocationListViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {
    var locationList: MutableLiveData<MutableList<Location>> = MutableLiveData()

    init {
        getLocations()
    }

    private fun getLocations() {
        viewModelScope.launch {
            try {
                val locationsDb = dbHelper.getLocations() as ArrayList<Location>
                locationList.value = locationsDb.asReversed()
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    fun deleteLocation(location: Location) {
        viewModelScope.launch {
            try {
                dbHelper.delete(location)
                locationList.value?.remove(location)
            } catch (e: Exception) {
                // handle error
            }
        }
    }
}
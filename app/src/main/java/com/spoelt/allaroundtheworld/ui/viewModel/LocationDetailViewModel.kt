package com.spoelt.allaroundtheworld.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoelt.allaroundtheworld.data.db.DatabaseHelper
import com.spoelt.allaroundtheworld.data.model.Location
import kotlinx.coroutines.launch

class LocationDetailViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun updateLocation(location: Location) {
        viewModelScope.launch {
            try {
                dbHelper.update(location)
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}
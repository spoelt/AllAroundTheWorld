package com.spoelt.allaroundtheworld.ui.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoelt.allaroundtheworld.data.db.DatabaseHelper
import com.spoelt.allaroundtheworld.data.model.Location
import kotlinx.coroutines.launch
import java.util.*

class CreateLocationViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {
    var imagePath = MutableLiveData<Uri>()
    private var caption = MutableLiveData<String>()
    private var locationName = MutableLiveData<String>()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun saveLocation() {
        viewModelScope.launch {
            try {
                val locationToInsert = Location(
                    UUID.randomUUID().toString(),
                    locationName.value,
                    caption.value,
                    imagePath.value.toString()
                )
                dbHelper.insert(locationToInsert)
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}
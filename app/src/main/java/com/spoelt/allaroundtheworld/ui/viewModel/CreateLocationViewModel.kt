package com.spoelt.allaroundtheworld.ui.viewModel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoelt.allaroundtheworld.data.db.DatabaseHelper
import com.spoelt.allaroundtheworld.data.model.Location
import kotlinx.coroutines.launch
import java.util.*

class CreateLocationViewModel(private val dbHelper: DatabaseHelper): ViewModel() {
    var imagePath = MutableLiveData<Uri>()
    var caption = MutableLiveData<String>()
    var locationName = MutableLiveData<String>()
    var errorMessage: MutableLiveData<String> = MutableLiveData()

    fun saveLocation() {
        viewModelScope.launch {
            try {
                val locationToInsert = Location(UUID.randomUUID().toString(), locationName.value, caption.value, imagePath.value.toString())
                dbHelper.insert(locationToInsert)
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }
}
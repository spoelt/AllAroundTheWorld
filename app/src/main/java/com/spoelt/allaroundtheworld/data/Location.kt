package com.spoelt.allaroundtheworld.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Location(
    val id: String,
    val name: String,
    val caption: String,
    val date: Date,
    val imagePath: String
) : Parcelable
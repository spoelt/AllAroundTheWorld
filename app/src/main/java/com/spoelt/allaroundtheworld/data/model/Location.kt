package com.spoelt.allaroundtheworld.data.model

import android.net.Uri
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
data class Location(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "caption") val caption: String?,
    //@ColumnInfo(name = "date") val date: Date?,
    @ColumnInfo(name = "image") val imagePath: String?
) : Parcelable
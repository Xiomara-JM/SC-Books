package com.example.sc_books.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
    val id: String,
    val volumeInfo: VolumeInfo
) : Parcelable
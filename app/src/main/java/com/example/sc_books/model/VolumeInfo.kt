package com.example.sc_books.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class VolumeInfo(
    val authors: List<String>?,
    val averageRating: Float,
    val categories: List<String>?,
    val description: String?,
    val imageLinks: ImageLinks?,
    val language: String,
    val pageCount: Int,
    val publishedDate: String,
    val publisher: String,
    val ratingsCount: Int,
    val subtitle: String?,
    val title: String
) : Parcelable
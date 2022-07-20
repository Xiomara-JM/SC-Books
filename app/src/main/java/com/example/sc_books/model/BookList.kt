package com.example.sc_books.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookList(
    val items: List<Item>?,
    val kind: String,
    val totalItems: Int
) : Parcelable


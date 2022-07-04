package com.example.sc_books.firebase.models

import com.google.firebase.firestore.Exclude

abstract class FirebaseEntity(
    @get:Exclude
    var documentId: String ?
)
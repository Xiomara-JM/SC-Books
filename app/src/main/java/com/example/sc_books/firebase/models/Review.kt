package com.example.sc_books.firebase.models

class Review (
    var user_id: String,
    var user_image: String,
    var user_name: String,
    var review_type_name: String,
    var review_favorite: Int,
    var book_name: String,
    var review_content: String,
    var book_image: String,

) : FirebaseEntity(documentId = null){
    constructor() : this(user_id = "", user_image = "", user_name = "",
        review_type_name = "", review_favorite = 0,book_name = "",review_content="",book_image="")

    override fun toString(): String {
        return "Purse ($user_id, $user_image, $user_name, $review_type_name, $review_favorite, $book_name,$review_content,$book_image)"
    }
}
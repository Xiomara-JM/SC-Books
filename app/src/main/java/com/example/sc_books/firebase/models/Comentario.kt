package com.example.sc_books.firebase.models

class Comentario(
    var user_id: String,
    var user_image: String,
    var user_name: String,
    var comentario_content: String,


    ) : FirebaseEntity(documentId = null){
    constructor() : this(user_id = "", user_image = "", user_name = "",comentario_content="",)

    override fun toString(): String {
        return "Comentario ($user_id, $user_image, $user_name,  $comentario_content)"
    }
}



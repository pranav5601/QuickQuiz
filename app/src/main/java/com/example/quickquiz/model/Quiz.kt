package com.example.quickquiz.model

import com.google.firebase.firestore.DocumentId

data class Quiz(
    @DocumentId
    val quiz_id: String = "",
    val name: String = "",
    val visibility: String = "",
    val desc: String = "",
    val level: String = "",
    val questions: Long = 0,
    val image: String = ""
)

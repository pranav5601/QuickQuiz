package com.example.quickquiz.model

import com.google.firebase.firestore.DocumentId

data class QuizQuestion(
    @DocumentId
    val questionId: String = "",
    val question: String = "",
    val answer: String = "",
    val opt_a: String = "",
    val opt_b: String = "",
    val opt_c: String = "",
    val timer: Long = 0
)

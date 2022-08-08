package com.example.quickquiz.helper

import android.content.Context
import com.example.quickquiz.model.Quiz
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FirebaseRepository(private val onTaskCompletion: OnTaskCompletion)  {

    private val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val quizRef: Query = firebaseFirestore.collection("QuizList").whereEqualTo("visibility","public")

    fun getQuizData(){
        quizRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful){
                onTaskCompletion.quizListDataAdded(task.result.toObjects(Quiz::class.java) as ArrayList<Quiz>)
            }else{
                task.exception?.let { onTaskCompletion.onError(it) }
            }

        }
    }

}

interface OnTaskCompletion{
    fun quizListDataAdded(quizList: ArrayList<Quiz>)
    fun onError(e: Exception)
}

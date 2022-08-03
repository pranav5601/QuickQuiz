package com.example.quickquiz.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickquiz.helper.FirebaseRepository
import com.example.quickquiz.helper.OnTaskCompletion
import com.example.quickquiz.model.Quiz

class QuizViewModel() : ViewModel(), OnTaskCompletion {

    private val quizListModelData: MutableLiveData<ArrayList<Quiz>> =
        MutableLiveData<ArrayList<Quiz>>()
    private val firebaseRepo: FirebaseRepository = FirebaseRepository(this)

    init {
        firebaseRepo.getQuizData()
    }

    fun getQuizData(): LiveData<ArrayList<Quiz>> {
        return quizListModelData
    }

    override fun quizListDataAdded(quizList: ArrayList<Quiz>) {
        quizListModelData.value = quizList
    }

    override fun onError(e: Exception) {
    }

}
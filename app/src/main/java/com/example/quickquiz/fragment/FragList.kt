package com.example.quickquiz.fragment

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quickquiz.R
import com.example.quickquiz.adapter.AdapterQuiz
import com.example.quickquiz.model.Quiz
import com.example.quickquiz.viewmodel.QuizViewModel
import kotlinx.android.synthetic.main.frag_list.*


class FragList : FragBase() {

    private var quizViewModel: QuizViewModel? = null
    var quizListViewModel: QuizViewModel? = null
    var quizAdapter: AdapterQuiz? = null

    override fun setUpView(view: View) {

        initVariables()
        initRecyclerView()
        quizViewModel?.getQuizData()?.observe(viewLifecycleOwner, Observer {data->
            quizAdapter?.quizList = data
            quizAdapter?.notifyDataSetChanged()
        })
    }

    private fun initRecyclerView() {
        rcv_quiz.layoutManager = LinearLayoutManager(baseContext)
        rcv_quiz.setHasFixedSize(true)
        rcv_quiz.adapter = quizAdapter
    }

    private fun initVariables() {

        quizViewModel = ViewModelProvider(baseContext)[QuizViewModel::class.java]
        quizAdapter = AdapterQuiz(baseContext)
        quizViewModel = QuizViewModel()



    }

    override fun getFragView() = R.layout.frag_list

}
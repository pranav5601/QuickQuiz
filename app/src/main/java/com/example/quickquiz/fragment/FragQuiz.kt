package com.example.quickquiz.fragment

import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.quickquiz.R
import com.example.quickquiz.model.QuizQuestion
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.frag_quiz.*
import kotlin.random.Random


class FragQuiz : FragBase() {


    private val getData: FragQuizArgs by navArgs()
    private var position: Long = 0
    private var quizId: String = ""
    private var fireStoreRep: FirebaseFirestore? = null
    private var totalQuestionToAsk: Long = 0
    private var allQuestionList: ArrayList<QuizQuestion>? = null
    private var questionsToAnswer: ArrayList<QuizQuestion>? = null
    private var canAns: Boolean? = true
    private var currentQuestion: Int? = 0
    private var countdownTimer: CountDownTimer? = null
    private var correctAns: Int = 0
    private var wrongAns: Int = 0


    override fun getFragView() = R.layout.frag_quiz

    override fun setUpView(view: View) {

        initVariables()
        getQuestions()
        initClick()

    }

    private fun initClick() {
        btn_option_one.setOnClickListener {
            verifyAns(btn_option_one)
        }
        btn_option_two.setOnClickListener {
            verifyAns(btn_option_two)
        }
        btn_option_three.setOnClickListener {
            verifyAns(btn_option_three)
        }
    }

    private fun verifyAns(btnAnswer: Button) {
        if (canAns!!){
            if (questionsToAnswer!![currentQuestion!!].answer == btnAnswer.text){
                Log.e("Answer","Correct")
                correctAns++
                btnAnswer.setBackgroundResource(R.drawable.btn_correct_answer_bg)
            }else{
                wrongAns++
                btnAnswer.setBackgroundResource(R.drawable.btn_wrong_ans_bg)
                Log.e("Answer","Wrong")
            }
            canAns = false
            countdownTimer?.cancel()

        }
    }

    private fun initVariables() {
        totalQuestionToAsk = getData.totalQuestion
        quizId = getData.quizId
        questionsToAnswer = ArrayList<QuizQuestion>()
        allQuestionList = ArrayList<QuizQuestion>()
    }

    private fun getQuestions() {

        fireStoreRep = FirebaseFirestore.getInstance()
        fireStoreRep?.collection("QuizList")?.document(quizId)?.collection("Questions")?.get()
            ?.addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    allQuestionList =
                        task.result.toObjects(QuizQuestion::class.java) as ArrayList<QuizQuestion>
                    txtQuestion.text = allQuestionList!![0].question

                    pickQuestion()
                    loadUI()
                    Log.e("QuizQue", "List : $allQuestionList")
                } else {
                    Log.e("QuizQue", task.exception?.message.toString())

                }
            }


    }

    private fun loadUI() {


        loadQuestion(1)

    }

    private fun loadQuestion(questionNumber: Int) {

        txtQuestion.text = questionsToAnswer!![questionNumber].question

        btn_option_one.text = questionsToAnswer!![questionNumber].opt_a
        btn_option_two.text = questionsToAnswer!![questionNumber].opt_b
        btn_option_three.text = questionsToAnswer!![questionNumber].opt_c

        txtQuestionNumber.text = questionNumber.toString()

        startTimer(questionNumber)

    }

    private fun startTimer(questionNumber: Int) {
        val timerCount = questionsToAnswer!![questionNumber].timer
        txtTimerCount.text = timerCount.toString()
        countdownTimer = object: CountDownTimer((timerCount + 1) * 1000, 10) {
            override fun onTick(time: Long) {
                txtTimerCount.text = (time / 1000).toString()
                val per = (time / (timerCount * 10))
                Log.e("timer", per.toString())
                proBarQuiz.apply {

                    startAngle = 180f
                    progress = per.toFloat()

                }
            }

            override fun onFinish() {
                Toast.makeText(baseContext, "Time finish.", Toast.LENGTH_SHORT).show()
                canAns = false
            }

        }
        countdownTimer?.start()
    }

    private fun pickQuestion() {

        for (i in 0 until totalQuestionToAsk) {
            val randomNum = rand(0, allQuestionList?.size!!)
            questionsToAnswer?.add(allQuestionList!![randomNum])
            allQuestionList?.removeAt(randomNum)

            Log.e("QuizQuestion: $i", " " + questionsToAnswer!![i.toInt()])


        }

    }

    private fun rand(from: Int, to: Int): Int {
        return Random.nextInt(to - from) + from
    }
}
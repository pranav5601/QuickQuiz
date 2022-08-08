package com.example.quickquiz.fragment

import android.os.Build
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
    private var quizName: String = ""
    private var quizId: String = ""
    private var totalQuestionToAsk: Long = 0
    private var allQuestionList: ArrayList<QuizQuestion>? = null
    private var questionsToAnswer: ArrayList<QuizQuestion>? = null
    private var canAns: Boolean? = true
    private var currentQuestion: Int = 0
    private var countdownTimer: CountDownTimer? = null
    private var correctAns: Int = 0
    private var wrongAns: Int = 0
    private var notAnswer: Int = 0


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
        btn_next_question.setOnClickListener {

            if (currentQuestion+1 == totalQuestionToAsk.toInt()) {

                submitResults()

            } else {
                currentQuestion++
                loadQuestion(currentQuestion)
                resetOption()
            }

        }
    }

    private fun submitResults() {

        val result: HashMap<String, Any> = HashMap<String, Any>()
        result["correct_ans"] = correctAns
        result["wrong_ans"] = wrongAns
        result["unanswered"] = notAnswer

        fireStoreRep?.collection("QuizList")?.document(quizId)?.collection("Result")
            ?.document(getUid())?.set(result)?.addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val fragResult = FragQuizDirections.actionFragQuizToFragResult(quizId)
                    navController?.navigate(fragResult)
                }else{
                    txtQuizTitle.text = task.exception?.message
                }
            }

    }

    private fun resetOption() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            btn_option_one.setBackgroundColor(resources.getColor(R.color.text_gray, null))
            btn_option_two.setBackgroundColor(resources.getColor(R.color.text_gray, null))
            btn_option_three.setBackgroundColor(resources.getColor(R.color.text_gray, null))

            btn_option_one.setTextColor(resources.getColor(R.color.black, null))
            btn_option_two.setTextColor(resources.getColor(R.color.black, null))
            btn_option_three.setTextColor(resources.getColor(R.color.black, null))
        }

        btn_option_one.isEnabled = true
        btn_option_two.isEnabled = true
        btn_option_three.isEnabled = true

        btn_next_question.visibility = View.INVISIBLE
        txtFeedBack.visibility = View.INVISIBLE
        canAns = true

    }

    private fun verifyAns(btnAnswer: Button) {
        if (canAns!!) {
            if (questionsToAnswer!![currentQuestion].answer == btnAnswer.text) {
                Log.e("Answer", "Correct")
                correctAns++
                txtFeedBack.text = getString(R.string.corerct_ans_feedback)
                btnAnswer.setBackgroundResource(R.drawable.btn_correct_answer_bg)
                txtFeedBack.setTextColor(resources.getColor(R.color.purple_200))
                btnAnswer.setTextColor(resources.getColor(R.color.white))
            } else {
                wrongAns++
                btnAnswer.setBackgroundResource(R.drawable.btn_wrong_ans_bg)
                txtFeedBack.text =
                    "Wrong answer \n Correct Answer: ${questionsToAnswer!![currentQuestion].answer}"
                txtFeedBack.setTextColor(resources.getColor(R.color.yellow))
                btnAnswer.setTextColor(resources.getColor(R.color.black))
                Log.e("Answer", "Wrong")
            }
            canAns = false
            countdownTimer?.cancel()
            setOptionBtnDisable()

            showNextBtn()

        }
    }

    private fun setOptionBtnDisable() {
        btn_option_one.isEnabled = false
        btn_option_two.isEnabled = false
        btn_option_three.isEnabled = false
    }

    private fun showNextBtn() {

        if (currentQuestion+1 == totalQuestionToAsk.toInt()) {
            btn_next_question.text = "Submit Answers"
            btn_next_question.visibility = View.VISIBLE
            btn_next_question.isEnabled = true

        }

        txtFeedBack.visibility = View.VISIBLE
        btn_next_question.visibility = View.VISIBLE
        btn_next_question.isEnabled = true

    }

    private fun initVariables() {
        totalQuestionToAsk = getData.totalQuestion
        quizId = getData.quizId
        quizName = getData.quizName
        questionsToAnswer = ArrayList<QuizQuestion>()
        allQuestionList = ArrayList<QuizQuestion>()

        txtQuizTitle.text = quizName

    }

    private fun getQuestions() {

        fireStoreRep.collection("QuizList").document(quizId).collection("Questions")
            .get()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    allQuestionList =
                        task.result.toObjects(QuizQuestion::class.java) as ArrayList<QuizQuestion>

                    txtQuestion.text = allQuestionList!![currentQuestion].question
                    pickQuestion()
                    loadUI()
                    Log.e("QuizQue", "List : $allQuestionList")


                } else {
                    Log.e("QuizQue", task.exception?.message.toString())

                }
            }


    }

    private fun loadUI() {


        loadQuestion(currentQuestion)

    }

    private fun loadQuestion(questionNumber: Int) {

        txtQuestion.text = questionsToAnswer!![questionNumber].question

        btn_option_one.text = questionsToAnswer!![questionNumber].opt_a
        btn_option_two.text = questionsToAnswer!![questionNumber].opt_b
        btn_option_three.text = questionsToAnswer!![questionNumber].opt_c

        val queNum = questionNumber + 1
        txtQuestionNumber.text = queNum.toString()

        startTimer(questionNumber)

    }

    private fun startTimer(questionNumber: Int) {
        val timerCount = questionsToAnswer!![questionNumber].timer
        txtTimerCount.text = timerCount.toString()
        countdownTimer = object : CountDownTimer((timerCount + 1) * 1000, 10) {
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
                txtFeedBack.text = "Time Up!!"
                txtFeedBack.setTextColor(resources.getColor(R.color.purple_200))
                notAnswer++
                showNextBtn()
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
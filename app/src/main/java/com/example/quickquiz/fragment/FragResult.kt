package com.example.quickquiz.fragment

import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.quickquiz.R
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.frag_result.*


class FragResult : FragBase() {


    private var quizId: String = ""
    private val getData: FragResultArgs by navArgs()

    override fun getFragView() = R.layout.frag_result

    override fun setUpView(view: View) {

        initVariables()
        getResultFromFirebase()
        initClick()




    }

    private fun initClick() {
        btnGoHome.setOnClickListener {
            navController?.navigate(R.id.action_fragResult_to_fragList)
        }
    }



    private fun getResultFromFirebase() {
        fireStoreRep.collection("QuizList").document(quizId).collection("Result").document(getUid())
            .get().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val res = task.result
                    txtCorrectAnsVal.text = res["correct_ans"].toString()
                    txtWrongAnswerVal.text = res["wrong_ans"].toString()
                    txtQuestionMissedVal.text = res["unanswered"].toString()

                    showResult(res)

                }
        }
    }

    private fun showResult(res: DocumentSnapshot) {

        val correct = res["correct_ans"] as Long
        val wrong = res["wrong_ans"] as Long
        val missed = res["unanswered"] as Long

        val total = correct + wrong + missed
        val per = (correct*100) / total

        txtPercentage.text = per.toString()
        proBarResult.apply {
            progress = per.toFloat()
        }

    }

    private fun initVariables() {

        quizId = getData.quizId


    }

}
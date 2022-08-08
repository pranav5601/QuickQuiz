package com.example.quickquiz.fragment

import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.quickquiz.R
import com.example.quickquiz.viewmodel.QuizViewModel
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.frag_details.*
import kotlinx.android.synthetic.main.frag_result.*

class FragDetails : FragBase() {

    private var quizViewModel: QuizViewModel? = null
    private var position: Int = 0
    private var quizId: String = ""
    private val getPosition: FragDetailsArgs by navArgs()
    private var totalQue: Long = 0

    override fun getFragView() = R.layout.frag_details

    override fun setUpView(view: View) {
        position = getPosition.position
        quizViewModel = ViewModelProvider(baseContext)[QuizViewModel::class.java]

        Log.e("getPosition", position.toString())

        initViewModel()
        initClick()



    }

    private fun initClick() {
        btnQuizStart.setOnClickListener {
            if (quizId.isNotEmpty()) {
                val passingAction =
                    FragDetailsDirections.actionFragDetailsToFragQuiz(totalQue, quizId)

                navController?.navigate(passingAction)
            }
        }
    }

    private fun initViewModel() {
        quizViewModel?.getQuizData()?.observe(viewLifecycleOwner, Observer { data ->
            txtQuizDetailTitle.text = data[position].name
            txtQuizDetailDesc.text = data[position].desc
            txtDetailDiffValue.text = data[position].level
            txtDetailTotalQueVal.text = data[position].questions.toString()
            quizId = data[position].quiz_id
            totalQue = data[position].questions
            getResultFromFirebase()
        })
    }

    private fun getResultFromFirebase() {
        fireStoreRep.collection("QuizList").document(quizId).collection("Result").document(getUid())
            .get().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val res = task.result
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

        txtDetailLastScoreVal.text = per.toString()


    }


}
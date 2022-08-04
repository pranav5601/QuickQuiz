package com.example.quickquiz.fragment

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.quickquiz.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.frag_splash.*

class FragSplash : FragBase() {

    private var firebaseAuth : FirebaseAuth? = null


    override fun getFragView() = R.layout.frag_splash

    override fun setUpView(view: View) {
        firebaseAuth = FirebaseAuth.getInstance()
        txt_start_feedback.text = "Checking user account..."

    }
    override fun onStart() {
        super.onStart()
        val firebaseUser = firebaseAuth?.currentUser
        if (firebaseUser == null){

            txt_start_feedback.text = "Creating account..."

            firebaseAuth?.signInAnonymously()?.addOnCompleteListener(baseContext){task ->


                if (task.isSuccessful){
                    txt_start_feedback.text = "Account created..."

                    navController?.navigate(R.id.action_fragSplash_to_fragList)

                }else{

                    task.exception?.message?.let { Log.d("login_error", it) }
                }


            }
        }else{
            txt_start_feedback.text = "Logged in.."
            navController?.navigate(R.id.action_fragSplash_to_fragList)
        }
    }

}
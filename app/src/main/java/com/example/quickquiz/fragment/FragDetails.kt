package com.example.quickquiz.fragment

import android.util.Log
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.quickquiz.R

class FragDetails : FragBase() {
    override fun getFragView() = R.layout.frag_details
    private val getPosition : FragDetailsArgs by navArgs()

    override fun setUpView(view: View) {
        val position = getPosition.position

        Log.e("getPosition",position.toString())

    }

}
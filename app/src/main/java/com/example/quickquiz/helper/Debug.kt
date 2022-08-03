package com.example.pranav.productbooking.helper

import android.util.Log

object Debug {

    private val DEBUG = true

    fun e(tag: String, msg: String) {
        if (DEBUG) {
            Log.e(tag, msg)
//            App.instance?.applicationContext?.let { Utils.getLogFile(it,  "\n$tag : $msg") }


        }
    }

    fun i(tag: String, msg: String) {
        if (DEBUG) {
            Log.i(tag, msg)
        }
    }

    fun w(tag: String, msg: String) {
        if (DEBUG) {
            Log.w(tag, msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (DEBUG) {
            Log.d(tag, msg)
        }
    }

}
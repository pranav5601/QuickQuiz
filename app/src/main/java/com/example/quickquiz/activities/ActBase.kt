package com.example.quickquiz.activities

import android.app.PendingIntent.getActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.pranav.productbooking.helper.IRoidLoader

abstract class ActBase : AppCompatActivity() {

    var iRoidLoader: IRoidLoader? = null


    fun showIroidLoader() {

        if (iRoidLoader == null)
            iRoidLoader = IRoidLoader(this)

        iRoidLoader?.show()
    }
    fun closeIroidLoader() {
        iRoidLoader?.dismiss()
    }

}
package com.example.quickquiz.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.quickquiz.activities.ActBase
import com.example.quickquiz.activities.ActMain

abstract class FragBase : Fragment()  {

    var navController : NavController? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getFragView(),container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setUpView(view)
    }

    abstract fun setUpView(view: View)

    abstract fun getFragView(): Int
    lateinit var baseContext : ActBase

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.baseContext = (context as? ActBase)!!
    }

    fun showLoader(){
        if(baseContext is ActMain){
            (baseContext as? ActMain)?.showIroidLoader()
        }

    }
    fun closeLoader(){
        if(baseContext is ActMain){
            (baseContext as? ActMain)?.closeIroidLoader()
        }

    }
}
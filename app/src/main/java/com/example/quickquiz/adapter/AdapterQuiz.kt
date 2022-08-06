package com.example.quickquiz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quickquiz.R
import com.example.quickquiz.model.Quiz
import kotlinx.android.synthetic.main.cell_quiz_list.view.*

class AdapterQuiz(private val baseContext: Context, private val listener: (Int) -> Unit): RecyclerView.Adapter<AdapterQuiz.MyViewHolder>() {

    var quizList: ArrayList<Quiz>? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View =LayoutInflater.from(baseContext).inflate(R.layout.cell_quiz_list,parent,false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(quizList?.get(position),baseContext, listener,position)
    }

    override fun getItemCount(): Int {
        return if(quizList != null) {
            quizList?.size!!
        }else{
            0
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(
            quizData: Quiz?,
            baseContext: Context,
            listener: (Int) -> Unit,
            position: Int
        ) = with(itemView.btn_view_quiz) {

            val imgUri = quizData?.image
            itemView.txtQuizName.text = quizData?.name
            itemView.txtQuizDesc.text = quizData?.desc
            itemView.txtQuizLevel.text = quizData?.level
            Glide.with(baseContext)
                .load(imgUri)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(itemView.img_quiz_poster)

            itemView.btn_view_quiz.setOnClickListener { listener(position) }


        }


    }
}
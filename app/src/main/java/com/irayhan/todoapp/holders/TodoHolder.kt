package com.irayhan.todoapp.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.irayhan.todoapp.R


class TodoHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    private val tvId = itemView.findViewById<TextView>(R.id.tv_id)
    private val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
    private val cbComplete = itemView.findViewById<TextView>(R.id.cb_complete)
}
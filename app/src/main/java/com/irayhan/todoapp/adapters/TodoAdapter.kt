package com.irayhan.todoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.irayhan.todoapp.R
import com.irayhan.todoapp.holders.TodoHolder
import com.irayhan.todoapp.interfaces.TodoInterface
import com.irayhan.todoapp.models.TodoModel
import kotlinx.android.synthetic.main.layout_todo.view.*

class TodoAdapter (
    private val context : Context,
    private val todoList : MutableList<TodoModel>,
    private val todoListener : TodoInterface
) : RecyclerView.Adapter<TodoHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_todo, parent, false)
        return TodoHolder(view)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }


    override fun onBindViewHolder(holder: TodoHolder, position: Int) {

        val todoModel = todoList[position]

        holder.itemView.tv_id.text = todoModel.id.toString()
        holder.itemView.tv_title.text = todoModel.title
        holder.itemView.cb_complete.isChecked = todoModel.completed

        holder.itemView.cb_complete.setOnCheckedChangeListener { buttonView, isChecked ->
            todoListener.onCheckBoxClick(todoModel, isChecked)
        }

        holder.itemView.setOnClickListener {
            todoListener.onTodoClick(todoModel)
        }

    }
}
package com.irayhan.todoapp.interfaces

import com.irayhan.todoapp.models.TodoModel

interface TodoInterface {

    fun onTodoClick(todoModel: TodoModel)
    fun onCheckBoxClick(todoModel: TodoModel, isChecked: Boolean)

}
package com.irayhan.todoapp.activities


import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import com.irayhan.todoapp.R
import com.irayhan.todoapp.interfaces.ApiInterface
import com.irayhan.todoapp.models.RetrofitMake
import com.irayhan.todoapp.models.TodoModel
import kotlinx.android.synthetic.main.activity_todo.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TodoActivity : AppCompatActivity() {



    private val TAG = "MSG"
    private var hasData = false
    private lateinit var todoModel: TodoModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        initialize()

    }



    private fun initialize(){

        sharedPreferences = getSharedPreferences("TodoData", MODE_PRIVATE)
        apiInterface = RetrofitMake().retrofitBuilder.create<ApiInterface>(ApiInterface::class.java)

        getData()
        setupListeners()

    }



    private fun getData(){

        if(intent.hasExtra("TodoModel")){

            hasData = true
            todoModel = intent.getSerializableExtra("TodoModel") as TodoModel

            et_user_id.setText(todoModel.userId.toString())
            et_title.setText(todoModel.title)
            cb_todo.isChecked = todoModel.completed

        }

    }


    private fun setupListeners(){

        btn_done.setOnClickListener {

            val userId = et_user_id.text.toString()
            val title = et_title.text.toString()
            val completed = cb_todo.isChecked

            todoModel = if(hasData){
                val id = todoModel.id
                TodoModel(userId.toInt(), id, title, completed)

            } else{
                val id = sharedPreferences.getInt("Total_Todo", 0) + 1;
                TodoModel(userId.toInt(), id, title, completed)
            }



            val call = apiInterface.createTodo(todoModel.userId, todoModel.id, todoModel.title, todoModel.completed)

            call.enqueue(object : Callback<TodoModel>{
                override fun onFailure(call: Call<TodoModel>, t: Throwable) {
                    Log.e(TAG, "onFailure: ", t )
                }

                override fun onResponse(call: Call<TodoModel>, response: Response<TodoModel>) {

                    if(response.isSuccessful){


                        if(hasData){
                            Toast.makeText(applicationContext, "Todo Update Success !", Toast.LENGTH_SHORT).show()
                            sharedPreferences.edit().putInt("Total_Todo", sharedPreferences.getInt("Total_Todo", 0) + 1).apply()
                        }
                        else{
                            Toast.makeText(applicationContext, "Todo Make Success !", Toast.LENGTH_SHORT).show()
                        }

                        Log.d(TAG, "onResponse: " + response.body())

                    }
                    else{

                        if(hasData){
                            Toast.makeText(applicationContext, "Todo Update Fail !", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(applicationContext, "Todo Make Fail !", Toast.LENGTH_SHORT).show()
                        }

                    }

                    finish()

                }

            })


        }





        btn_cancel.setOnClickListener {
            finish()
        }

    }






}
package com.irayhan.todoapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.irayhan.todoapp.R
import com.irayhan.todoapp.adapters.TodoAdapter
import com.irayhan.todoapp.interfaces.ApiInterface
import com.irayhan.todoapp.interfaces.TodoInterface
import com.irayhan.todoapp.models.RetrofitMake
import com.irayhan.todoapp.models.TodoModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MainActivity : AppCompatActivity() {


    private val TAG = "MSG"
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var apiInterface: ApiInterface


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onStart() {
        super.onStart()
        initialize()
    }


    private fun initialize(){


        apiInterface = RetrofitMake().retrofitBuilder.create<ApiInterface>(ApiInterface::class.java)

        getAllTodo()
        setupListeners()

    }



    private fun setupListeners(){

        fab_main.setOnClickListener {
            startActivity(Intent(this, TodoActivity::class.java))
        }

    }



    private fun getAllTodo(){


        val call = apiInterface.getAllTodo()

        call.enqueue(object : Callback<List<TodoModel>> {
            override fun onFailure(call: Call<List<TodoModel>>, t: Throwable) {
                Log.e("MSG", t.toString())
            }

            override fun onResponse(
                call: Call<List<TodoModel>>,
                response: Response<List<TodoModel>>
            ) {

                val todoList = response.body()

                // save total data
                getSharedPreferences("TodoData", MODE_PRIVATE).edit().putInt("Total_Todo", todoList!!.size).apply()

                todoAdapter = TodoAdapter(
                    applicationContext,
                    todoList as MutableList<TodoModel>,
                    object : TodoInterface {

                        override fun onTodoClick(todoModel: TodoModel) {
                            val intent = Intent(applicationContext, TodoActivity::class.java)
                            intent.putExtra("TodoModel", todoModel)
                            startActivity(intent)
                        }

                        override fun onCheckBoxClick(todoModel: TodoModel, isChecked: Boolean) {
                            todoModel.completed = isChecked
                            updateTodo(todoModel)
                        }

                    })

                rv_main.adapter = todoAdapter


            }

        })



    }


    private fun updateTodo(todoModel: TodoModel){

        val call = apiInterface.updateTodo(todoModel.id, todoModel)

        call.enqueue(object: Callback<TodoModel>{

            override fun onFailure(call: Call<TodoModel>, t: Throwable) {
                Log.e(TAG, "onFailure: ", t )
            }

            override fun onResponse(call: Call<TodoModel>, response: Response<TodoModel>) {

                if(response.isSuccessful){
                    Log.d(TAG, "onResponse: " + response.body())
                }

            }

        })

    }

}
package com.irayhan.todoapp.interfaces

import com.irayhan.todoapp.models.TodoModel
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {

    @FormUrlEncoded
    @POST("todos")
    fun createTodo(
        @Field("userId") userId: Int?,
        @Field("id") id: Int?,
        @Field("title") title: String?,
        @Field("completed") completed: Boolean?
    ): Call<TodoModel>


    @GET("todos")
    fun getAllTodo(): Call<List<TodoModel>>


    @PUT("todos/{id}")
    fun updateTodo(@Path("id") id: Int, @Body todoModel: TodoModel): Call<TodoModel>


}
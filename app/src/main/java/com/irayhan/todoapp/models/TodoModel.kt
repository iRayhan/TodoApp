package com.irayhan.todoapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TodoModel(
    @SerializedName("userId") val userId : Int,
    @SerializedName("id") val id : Int,
    @SerializedName("title") val title : String,
    @SerializedName("completed") var completed : Boolean
) : Serializable
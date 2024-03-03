package com.example.bagshop.model.repository.comments

import android.util.Log
import com.example.bagshop.model.data.Comment
import com.example.bagshop.model.data.CommentResponse
import com.example.bagshop.model.net.ApiService
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class CommentsRepositoryImpl(

    private val apiService: ApiService,
) : CommentsRepository {
    override suspend fun getAllComments(productId: String): List<Comment> {
        val jsonObject = JsonObject().apply {
            addProperty("productId", productId)
        }
        val data = apiService.getAllComments(jsonObject)

        if(data.success) {
            return data.comments
        }

        return listOf()


    }
    override suspend fun addNewComment(productId: String, text: String, IsSuccess: (String) -> Unit) {

        val jsonObject = JsonObject().apply {
            addProperty("productId" , productId)
            addProperty("text" , text)
        }
        val result = apiService.addNewComment(jsonObject)

        if(result.success) {
            IsSuccess.invoke(result.message)
        } else {
            IsSuccess.invoke("Comment not added")
        }

    }



}


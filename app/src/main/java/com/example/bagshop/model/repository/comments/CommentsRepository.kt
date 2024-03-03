package com.example.bagshop.model.repository.comments

import com.example.bagshop.model.data.Comment
import com.example.bagshop.model.data.CommentResponse


interface CommentsRepository{


     suspend fun getAllComments(productId:String) : List<Comment>

    suspend fun addNewComment(productId: String,text:String,isSuccess:(String)->Unit)

}
package com.example.bagshop.model.data


import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("comments")
    val comments: List<Comment>,
    @SerializedName("success")
    val success: Boolean
)

data class Comment(
    @SerializedName("commentId")
    val commentId: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("userEmail")
    val userEmail: String
)
data class AddNewCommentResponse(
    val message: String,
    val success: Boolean
)
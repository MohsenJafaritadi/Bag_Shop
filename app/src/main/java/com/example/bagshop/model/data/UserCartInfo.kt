package com.example.bagshop.model.data

import com.google.gson.annotations.SerializedName

data class UserCartInfo(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("productList")
    val productList: List<Product>,
    @SerializedName("message")
    val message: String,
    @SerializedName("totalPrice")
    val totalPrice: Int,
)
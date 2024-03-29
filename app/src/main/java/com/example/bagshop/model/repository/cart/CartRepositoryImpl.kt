package com.example.bagshop.model.repository.cart

import com.example.bagshop.model.data.Comment
import com.example.bagshop.model.data.UserCartInfo
import com.example.bagshop.model.net.ApiService
import com.google.gson.JsonObject

class CartRepositoryImpl(

    private val apiService: ApiService,
) : CartRepository {
    override suspend fun addToCart(productId: String): Boolean {


        val jsonObject = JsonObject().apply {
            addProperty("productId", productId)
        }
        val result = apiService.addProductToCart(jsonObject)
        return result.success
    }

    override suspend fun getCartSize(): Int {
        val result = apiService.getUserCart()
        if (result.success) {

            var counter = 0
            result.productList.forEach {
                counter += (it.quantity ?: "0").toInt()
            }

            return counter
        }
        return 0
    }

    override suspend fun removeFromCart(productId: String):Boolean {
        val jsonObject = JsonObject().apply {
            addProperty("productId", productId)
        }
        val result = apiService.removeFromCart(jsonObject)
        return result.success
    }

    override suspend fun getUserCartInfo(): UserCartInfo {
       return  apiService.getUserCart()
    }

}
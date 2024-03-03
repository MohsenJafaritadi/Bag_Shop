package com.example.bagshop.model.repository.cart

import com.example.bagshop.model.data.Comment
import com.example.bagshop.model.data.UserCartInfo


interface CartRepository{

        suspend fun addToCart(productId:String):Boolean
        suspend fun getCartSize():Int

        suspend fun removeFromCart(productId:String):Boolean
        suspend fun getUserCartInfo():UserCartInfo

}
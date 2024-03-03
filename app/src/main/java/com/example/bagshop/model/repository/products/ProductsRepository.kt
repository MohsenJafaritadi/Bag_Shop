package com.example.bagshop.model.repository.products

import com.example.bagshop.model.data.Ads
import com.example.bagshop.model.data.Product

interface ProductsRepository {

    suspend fun getAllProducts(isInternetConnected:Boolean):List<Product>


    suspend fun getAllAds(isInternetConnected:Boolean):List<Ads>


    suspend fun getAllProductsByCategory(category:String):List<Product>

    suspend fun getProductById(productId: String):Product
}
package com.example.bagshop.ui.features.main

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bagshop.model.data.Ads
import com.example.bagshop.model.data.Product
import com.example.bagshop.model.repository.cart.CartRepository
import com.example.bagshop.model.repository.products.ProductsRepository
import com.example.bagshop.model.repository.user.UserRepository
import com.example.bagshop.util.coroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(
    private val productRepository: ProductsRepository,
    private  val cartRepository: CartRepository,
    isInterNetConnected: Boolean,
) : ViewModel() {

    val dataProduct = mutableStateOf<List<Product>>(listOf())

    val dataAds = mutableStateOf<List<Ads>>(listOf())
    val showProgressBar = mutableStateOf(false)
    val badgeNumber = mutableStateOf(0)

    init {
        refreshAllDataFormat(isInterNetConnected)
    }

    fun refreshAllDataFormat(isInterNetConnected: Boolean) {

        viewModelScope.launch(coroutineExceptionHandler) {

            if (isInterNetConnected)
                showProgressBar.value = true
            delay(1200)
            val newDataProduct = async { productRepository.getAllProducts(isInterNetConnected) }
            val newDataAds = async { productRepository.getAllAds(isInterNetConnected) }


            updateData(newDataProduct.await(), newDataAds.await())
            showProgressBar.value = false

        }
    }

    fun updateData(product: List<Product>, ads: List<Ads>) {

        dataProduct.value = product
        dataAds.value = ads
    }

     fun loadBadgeNumber() {
        viewModelScope.launch(coroutineExceptionHandler) {

            badgeNumber.value = cartRepository.getCartSize()

        }
    }

}
package com.example.bagshop.ui.features.category

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bagshop.model.data.Ads
import com.example.bagshop.model.data.Product
import com.example.bagshop.model.repository.products.ProductsRepository
import com.example.bagshop.model.repository.user.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val productRepository: ProductsRepository
) : ViewModel() {

    val dataProduct = mutableStateOf<List<Product>>(listOf())





    fun loadDataByCategory(category:String) {

        viewModelScope.launch {


            val dataFromLocal=productRepository.getAllProductsByCategory(category)

            dataProduct.value=dataFromLocal


        }
    }



}
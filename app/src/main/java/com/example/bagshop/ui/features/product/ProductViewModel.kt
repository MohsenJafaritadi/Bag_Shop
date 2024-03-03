package com.example.bagshop.ui.features.product


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bagshop.model.data.Comment
import com.example.bagshop.model.repository.cart.CartRepository
import com.example.bagshop.model.repository.comments.CommentsRepository
import com.example.bagshop.model.repository.products.ProductsRepository
import com.example.bagshop.util.EMPTY_PRODUCT
import com.example.bagshop.util.coroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ProductViewModel(

    private val productRepository: ProductsRepository,
    private val commentRepository: CommentsRepository,
    private val cartRepository: CartRepository,

    ) : ViewModel() {

    val thisProduct = mutableStateOf(EMPTY_PRODUCT)

    val comments = mutableStateOf(listOf<Comment>())

    val isAddingProduct = mutableStateOf(false)
    val badgeNumber = mutableStateOf(0)


    fun loadData(productId: String, isInternetConnected: Boolean) {
        loadProductFromCache(productId)
        if (isInternetConnected) {
            loadAllComments(productId)
            loadBadgeNumber()
        }
    }


    private fun loadProductFromCache(productId: String) {

        viewModelScope.launch(coroutineExceptionHandler) {

            thisProduct.value = productRepository.getProductById(productId)

        }


    }

    private fun loadBadgeNumber() {
        viewModelScope.launch(coroutineExceptionHandler) {

            badgeNumber.value = cartRepository.getCartSize()

        }
    }

    private fun loadAllComments(productId: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            try {
                comments.value = commentRepository.getAllComments(productId)
            } catch (e: Exception) {
                e.message.toString()
            }

//            Log.e("pro", comments.value.toString())
//            Log.e("pro", productId, )

        }
    }

    fun addNewComment(productId: String, text: String, IsSuccess: (String) -> Unit) {


        viewModelScope.launch(Dispatchers.IO) {

            commentRepository.addNewComment(productId, text, IsSuccess)

            delay(100)
            comments.value = commentRepository.getAllComments(productId)
        }
    }

    fun addProductToCart(productId: String, AddingToCartResult: (String) -> Unit) {
        viewModelScope.launch(coroutineExceptionHandler) {

            isAddingProduct.value = true
            val result = cartRepository.addToCart(productId)
            delay(500)
            isAddingProduct.value = false
            if (result) {
                AddingToCartResult.invoke("Product  Added")
            }


        }
    }

}
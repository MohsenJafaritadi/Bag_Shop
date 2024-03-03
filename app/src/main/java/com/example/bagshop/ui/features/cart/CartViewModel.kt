package com.example.bagshop.ui.features.cart

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

class CartViewModel(
   private val cartRepository: CartRepository
):ViewModel(){

   val productList= mutableStateOf(listOf<Product>())
   val totalPrice= mutableStateOf(0)
   val isChangeNumber= mutableStateOf(Pair("",false))

fun loadCartData(){

   viewModelScope.launch(coroutineExceptionHandler) {
      val data=cartRepository.getUserCartInfo()

      productList.value=data.productList
      totalPrice.value=data.totalPrice
   }


}

   fun addItem(productId:String){
      viewModelScope.launch(coroutineExceptionHandler) {
         isChangeNumber.value=isChangeNumber.value.copy(productId,true)
         val isSuccess=cartRepository.addToCart(productId)

         if (isSuccess){
            loadCartData()
         }
         delay(100)
         isChangeNumber.value=isChangeNumber.value.copy(productId,false)
      }
   }
   fun removeItem(productId:String){
      viewModelScope.launch(coroutineExceptionHandler) {
         isChangeNumber.value=isChangeNumber.value.copy(productId,true)
         val isSuccess=cartRepository.removeFromCart(productId)

         if (isSuccess){
            loadCartData()
         }
         delay(100)
         isChangeNumber.value=isChangeNumber.value.copy(productId,false)
      }
   }



}
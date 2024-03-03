package com.example.bagshop.ui.features.signIn

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bagshop.model.repository.user.UserRepository
import com.example.bagshop.util.coroutineExceptionHandler
import kotlinx.coroutines.launch

class SignInViewModel(private val userRepository: UserRepository) : ViewModel() {


    val email = MutableLiveData("")
    val password = MutableLiveData("")


    fun singInUser(LoggingEvent: (String) -> Unit) {


        viewModelScope.launch(coroutineExceptionHandler) {
            val result = userRepository.signIn(email.value!!, password.value!!)
            LoggingEvent(result)
        }


    }
}
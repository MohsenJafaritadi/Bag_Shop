package com.example.bagshop.ui.features.signUp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bagshop.model.repository.user.UserRepository
import com.example.bagshop.util.coroutineExceptionHandler
import kotlinx.coroutines.launch

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {

    val name = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val confirmPassword = MutableLiveData("")

    fun singUpUser( LoggingEvent:(String)->Unit) {

        viewModelScope.launch (coroutineExceptionHandler){

            val result = userRepository.signUp(name.value!!, email.value!!, password.value!!)

            LoggingEvent(result)

        }
    }
}
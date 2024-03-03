package com.example.bagshop.di


import android.content.Context
import androidx.room.Room
import com.example.bagshop.model.db.AppDataBase
import com.example.bagshop.model.net.createApiService
import com.example.bagshop.model.repository.cart.CartRepository
import com.example.bagshop.model.repository.cart.CartRepositoryImpl
import com.example.bagshop.model.repository.comments.CommentsRepository
import com.example.bagshop.model.repository.comments.CommentsRepositoryImpl
import com.example.bagshop.model.repository.products.ProductRepositoryImpl
import com.example.bagshop.model.repository.products.ProductsRepository
import com.example.bagshop.model.repository.user.UserRepository
import com.example.bagshop.model.repository.user.UserRepositoryImpl
import com.example.bagshop.ui.features.cart.CartViewModel
import com.example.bagshop.ui.features.category.CategoryViewModel
import com.example.bagshop.ui.features.main.MainViewModel
import com.example.bagshop.ui.features.product.ProductViewModel
import com.example.bagshop.ui.features.profile.ProfileViewModel
import com.example.bagshop.ui.features.signIn.SignInViewModel
import com.example.bagshop.ui.features.signUp.SignUpViewModel
import dev.burnoo.cokoin.get
import org.koin.android.ext.koin.androidContext

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module


val myModules = module() {


    single { androidContext().getSharedPreferences("data", Context.MODE_PRIVATE) }
    single { createApiService() }

    single { Room.databaseBuilder(androidContext(), AppDataBase::class.java, "app_dataBase.db").build() }
    single<UserRepository> { UserRepositoryImpl(get(), get()) }

    single<ProductsRepository> { ProductRepositoryImpl(get(), get<AppDataBase>().productDao()) }
    single<CartRepository> { CartRepositoryImpl(get(),) }

    single<CommentsRepository> { CommentsRepositoryImpl(get()) }

    viewModel {ProfileViewModel(get())}
    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { (isNetConnected: Boolean) -> MainViewModel(get(), get() , isNetConnected) }
    viewModel { CategoryViewModel(get()) }
    viewModel { ProductViewModel(get(), get(), get()) }
    viewModel {CartViewModel( get()  ) }

}
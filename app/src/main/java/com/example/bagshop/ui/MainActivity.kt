package com.example.bagshop.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bagshop.di.myModules
import com.example.bagshop.model.repository.TokenInMemory
import com.example.bagshop.model.repository.user.UserRepository
import com.example.bagshop.ui.features.IntroScreen
import com.example.bagshop.ui.features.cart.CartScreen
import com.example.bagshop.ui.features.category.CategoryScreen
import com.example.bagshop.ui.features.main.MainScreen
import com.example.bagshop.ui.features.product.ProductScreen
import com.example.bagshop.ui.features.profile.ProfileScreen
import com.example.bagshop.ui.features.signIn.SingInScreen
import com.example.bagshop.ui.features.signUp.SingUpScreen
import com.example.bagshop.ui.theme.BackgroundMain
import com.example.bagshop.ui.theme.BagShopTheme
import com.example.bagshop.util.KEY_CATEGORY_ARG
import com.example.bagshop.util.KEY_PRODUCT_ARG
import com.example.bagshop.util.MyScreens
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.get
import dev.burnoo.cokoin.navigation.KoinNavHost
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Koin(appDeclaration = {
                androidContext(this@MainActivity)
                modules(myModules)

            }) {
                BagShopTheme {
                    Surface(
                        color = BackgroundMain,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val userRepository:UserRepository=get()
                        userRepository.loadToken()
                        BagShopUI()
                    }
                }
            }

        }
    }
}


@Composable
fun BagShopUI() {

    val navController = rememberNavController()
    KoinNavHost(
        navController = navController,
        startDestination = MyScreens.MainScreen.route) {

        composable(MyScreens.MainScreen.route) {
            if (TokenInMemory.token != null ) {
                MainScreen()
            } else {
                IntroScreen()
            }
        }
        composable(route = MyScreens.ProductScreen.route + "/" + "{$KEY_PRODUCT_ARG}",
            arguments = listOf(navArgument(KEY_PRODUCT_ARG) {
                type = NavType.StringType
            })
        ) {
            ProductScreen(it.arguments!!.getString(KEY_PRODUCT_ARG, ""))
        }
        composable(route = MyScreens.CategoryScreen.route + "/" + "{$KEY_CATEGORY_ARG}",
            arguments = listOf(navArgument(KEY_CATEGORY_ARG) {
                type = NavType.StringType
            })

        ) {
            CategoryScreen(it.arguments!!.getString(KEY_CATEGORY_ARG, ""))
        }

        composable(MyScreens.ProfileScreen.route) {
            ProfileScreen()
        }
        composable(MyScreens.CartScreen.route) {
            CartScreen()
        }
        composable(MyScreens.SingUpScreen.route) {
            SingUpScreen()
        }
        composable(MyScreens.SingInScreen.route) {
            SingInScreen()
        }


    }

}








@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BagShopTheme {


        Surface(color = BackgroundMain, modifier = Modifier.fillMaxSize()) {
            BagShopUI()
        }
    }
}
package com.example.bagshop.util

sealed class MyScreens(val route: String) {


    object MainScreen : MyScreens("mainScreen")
    object ProductScreen : MyScreens("productScreen")
    object CategoryScreen : MyScreens("categoryScreen")
    object ProfileScreen : MyScreens("profileScreen")
    object CartScreen : MyScreens("cartScreen")
    object SingUpScreen : MyScreens("singUpScreen")
    object SingInScreen : MyScreens("singInScreen")


}

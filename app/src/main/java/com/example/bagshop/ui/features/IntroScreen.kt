package com.example.bagshop.ui.features

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.bagshop.R
import com.example.bagshop.ui.BagShopUI
import com.example.bagshop.ui.theme.BackgroundMain
import com.example.bagshop.ui.theme.BagShopTheme
import com.example.bagshop.ui.theme.Blue
import com.example.bagshop.util.MyScreens
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BagShopTheme {
        Surface(color = BackgroundMain, modifier = Modifier.fillMaxSize()) {
            IntroScreen()
        }
    }
}

@Composable
fun IntroScreen() {

    val uiController= rememberSystemUiController()
    SideEffect {
        uiController.setStatusBarColor(Blue)
    }
    val navigation = getNavController()



    Image(modifier = Modifier.fillMaxSize(), painter = painterResource(R.drawable.img_intro),
        contentDescription = "null",
        contentScale = ContentScale.Crop
    )

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.78f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {

        Button(
            modifier = Modifier.fillMaxWidth(0.7f),
            onClick = {

                navigation.navigate(MyScreens.SingUpScreen.route)
            }) {

            Text(text = "Sign Up", color = Color.White)

        }
        Button(
            modifier = Modifier.fillMaxWidth(0.7f),

            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            onClick = {
                navigation.navigate(MyScreens.SingInScreen.route)
            }) {

            Text(text = "Sign In", color = Blue)

        }

    }

}

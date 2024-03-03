package com.example.bagshop.ui.features.signIn

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bagshop.R
import com.example.bagshop.ui.features.signUp.SignUpViewModel
import com.example.bagshop.ui.theme.BackgroundMain
import com.example.bagshop.ui.theme.BagShopTheme
import com.example.bagshop.ui.theme.Blue
import com.example.bagshop.ui.theme.Shapes
import com.example.bagshop.util.MyScreens
import com.example.bagshop.util.NetworkChecker
import com.example.bagshop.util.VALUE_SUCCESS
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BagShopTheme {
        Surface(color = BackgroundMain,
            modifier = Modifier.fillMaxSize()) {
            SingInScreen()
        }
    }
}


@Composable
fun SingInScreen() {

    SignInScreen()
}


@Composable
fun SignInScreen() {
    val uiController = rememberSystemUiController()
    SideEffect {
        uiController.setStatusBarColor(Blue)
    }
    val context = LocalContext.current
    val navigation = getNavController()
    val viewModel = getNavViewModel<SignInViewModel>()

    clearInputs(viewModel)
    Box() {
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
            .background(Blue)
        )
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally) {

            IconApp()
            MainCardView(navigation, viewModel) {
                viewModel.singInUser {
                    if (it == VALUE_SUCCESS) {

                        navigation.navigate(MyScreens.MainScreen.route) {
                            popUpTo(MyScreens.MainScreen.route) {
                                inclusive = true
                            }
                        }
                    } else {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }


}

@Composable
fun IconApp() {

    Surface(modifier = Modifier
        .clip(CircleShape)
        .size(64.dp)) {

        Image(
            modifier = Modifier.padding(14.dp),
            painter = painterResource(id = R.drawable.ic_icon_app),
            contentDescription = "null")
    }
}

@Composable
fun MainCardView(navigation: NavController, viewModel: SignInViewModel, SingUpEvent: () -> Unit) {

    val email = viewModel.email.observeAsState("")
    val password = viewModel.password.observeAsState("")
    val context = LocalContext.current


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = 10.dp,
        shape = Shapes.medium,


        ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(modifier = Modifier.padding(top = 18.dp, bottom = 18.dp),
                text = "Sing In",
                style = TextStyle(color = Blue, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            )

            MainTextField(email.value, R.drawable.ic_email, "your  Email") {
                viewModel.email.value = it
            }
            PasswordTextField(password.value,
                R.drawable.ic_password,
                "Password") { viewModel.password.value = it }

            Button(
                onClick = {

                    if (email.value.isNotEmpty() && password.value.isNotEmpty()) {

                        if (Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
                            if (NetworkChecker(context).isInternetConnected) {
                                SingUpEvent.invoke()
                            } else {
                                Toast.makeText(context,
                                    "please connect to internet",
                                    Toast.LENGTH_SHORT).show()
                            }

                        } else {
                            Toast.makeText(context,
                                "email format is not true ",
                                Toast.LENGTH_SHORT)
                                .show()
                        }


                    } else {
                        Toast.makeText(context, "please write data first", Toast.LENGTH_SHORT)
                            .show()
                    }

                },
                modifier = Modifier.padding(top = 28.dp, bottom = 8.dp),
            ) {

                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Log in",
                )


            }

            Row(
                modifier = Modifier.padding(bottom = 18.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Don't have an Account")

                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = {
                    navigation.navigate(MyScreens.SingUpScreen.route) {
                        popUpTo(MyScreens.SingInScreen.route) {
                            inclusive = true
                        }
                    }

                }) {
                    Text(text = "Register Here ", color = Blue)
                }
            }
        }
    }
}

@Composable
fun MainTextField(
    edtValue: String, icon: Int, hint: String, onValueChanges: (String) -> Unit,
) {
    OutlinedTextField(
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        label = { Text(hint) },
        value = edtValue,
        singleLine = true,
        onValueChange = onValueChanges,
        placeholder = { Text(hint) },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = 12.dp),
        shape = Shapes.medium,
        leadingIcon = { Icon(painterResource(icon), "null") }


    )
}

@Composable
fun PasswordTextField(
    edtValue: String, icon: Int, hint: String, onValueChanges: (String) -> Unit,
) {
    val passwordVisible = remember { mutableStateOf(false) }
    OutlinedTextField(
        label = { Text(hint) },
        value = edtValue,
        singleLine = true,
        onValueChange = onValueChanges,
        placeholder = { Text(hint) },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = 12.dp),
        shape = Shapes.medium,
        leadingIcon = { Icon(painterResource(icon), "null") },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {

            val image =
                if (passwordVisible.value) painterResource(R.drawable.ic_visible)
                else painterResource(R.drawable.ic_invisible)

            Icon(painter = image,
                contentDescription = "null",
                modifier = Modifier.clickable { passwordVisible.value = !passwordVisible.value })
        }

    )
}


fun clearInputs(viewModel: SignInViewModel) {
    viewModel.email.value = ""

    viewModel.password.value = ""

}
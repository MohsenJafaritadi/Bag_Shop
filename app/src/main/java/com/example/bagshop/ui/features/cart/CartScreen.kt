package com.example.bagshop.ui.features.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.bagshop.R
import com.example.bagshop.model.data.Product
import com.example.bagshop.ui.theme.Blue
import com.example.bagshop.ui.theme.PriceBackground
import com.example.bagshop.ui.theme.Shapes
import com.example.bagshop.util.MyScreens
import com.example.bagshop.util.stylePrice
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel


@Composable
fun CartScreen() {

    val context = LocalContext.current
    val viewModel = getNavViewModel<CartViewModel>()

    val getDataDialogState = remember { mutableStateOf(false) }
    val navigation = getNavController()
    viewModel.loadCartData()


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {


        Column(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 74.dp)) {

            CartToolbar(OnBackClicked = { navigation.popBackStack() },
                OnProfileClicked = { navigation.navigate(MyScreens.ProfileScreen.route) })


            if (viewModel.productList.value.isNotEmpty()) {

                CartList(data = viewModel.productList.value,
                    isChangingNumber = viewModel.isChangeNumber.value,
                    OnAddItemClicked = { viewModel.addItem(it) },
                    OnRemoveItemClicked = { viewModel.removeItem(it) },
                    OnItemClicked ={ navigation.navigate(MyScreens.ProductScreen.route + "/" + it)
                })
            } else {

                NoDataAnimation()
            }
        }
    }


}

@Composable
fun NoDataAnimation() {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.no_data)
    )

    LottieAnimation(

        composition = composition,
        iterations = LottieConstants.IterateForever

    )



}

@Composable
fun CartToolbar(

    OnBackClicked: () -> Unit,
    OnProfileClicked: () -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { OnBackClicked.invoke() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)

            }

        },
        elevation = 2.dp,
        backgroundColor = Color.White,
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(text = "My Cart",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 24.dp))
        },
        actions = {

            IconButton(onClick = { OnProfileClicked.invoke() },
                modifier = Modifier.padding(end = 6.dp)) {


                Icon(Icons.Default.Person, null)

            }

        }

    )


}

@Composable
fun CartList(
    data: List<Product>,
    isChangingNumber: Pair<String, Boolean>,
    OnAddItemClicked: (String) -> Unit,
    OnRemoveItemClicked: (String) -> Unit,
    OnItemClicked: (String) -> Unit,
) {

    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 16.dp)

    ) {
        items(data.size) {

            CartItem(data = data[it],
                isChangingNumber = isChangingNumber,
                OnAddItemClicked = OnAddItemClicked,
                OnRemoveItemClicked = OnRemoveItemClicked,
                OnItemClicked = OnItemClicked)
        }
    }
}

@Composable
fun CartItem(
    data: Product,
    isChangingNumber: Pair<String, Boolean>,
    OnAddItemClicked: (String) -> Unit,
    OnRemoveItemClicked: (String) -> Unit,
    OnItemClicked: (String) -> Unit,
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        .clickable { OnItemClicked.invoke(data.productId) },
        elevation = 4.dp,
        shape = Shapes.large) {

        Column {
            AsyncImage(
                model = data.imgUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
            )

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {

                Column(modifier = Modifier.padding(10.dp)) {

                    Text(text = data.name,
                        style = TextStyle(fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = "From " + data.category + "Group",
                        style = TextStyle(
                            fontSize = 14.sp,

                            )
                    )
                    Text(
                        modifier = Modifier.padding(top = 18.dp),
                        text = "Product authenticity guarantee",
                        style = TextStyle(
                            fontSize = 14.sp,

                            )
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = "Available in Stock to ship",
                        style = TextStyle(
                            fontSize = 14.sp,

                            )
                    )

                    Surface(modifier = Modifier
                        .padding(top = 18.dp, bottom = 6.dp)
                        .clip(Shapes.large), color = PriceBackground) {

                        Text(modifier = Modifier.padding(top = 6.dp,
                            bottom = 6.dp,
                            start = 8.dp,
                            end = 8.dp), text = stylePrice(
                            (data.price.toInt() * (data.quantity ?: "1").toInt()).toString()
                        ),
                            style = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        )

                    }


                }


                Surface(
                    modifier = Modifier
                        .padding(bottom = 14.dp, end = 8.dp)
                        .align(Alignment.Bottom)


                ) {
                    Card(border = BorderStroke(2.dp, Blue)) {


                        Row(verticalAlignment = Alignment.CenterVertically) {


                            if (data.quantity?.toInt() == 1) {
                                IconButton(onClick = { OnRemoveItemClicked.invoke(data.productId) }) {
                                    Icon(modifier = Modifier.padding(end = 4.dp, start = 4.dp),
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null)
                                }
                            } else {
                                IconButton(onClick = { OnRemoveItemClicked.invoke(data.productId) }) {
                                    Icon(modifier = Modifier.padding(end = 4.dp, start = 4.dp),
                                        painter = painterResource(id = R.drawable.ic_minus),
                                        contentDescription = null)
                                }
                            }

                            if (isChangingNumber.first == data.productId && isChangingNumber.second) {
                                Text(text = "...",
                                    style = TextStyle(fontSize = 18.sp),
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )
                            } else {
                                Text(text = data.quantity ?: "1",
                                    style = TextStyle(fontSize = 18.sp),
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                            }
                            IconButton(onClick = { OnAddItemClicked.invoke(data.productId) }) {
                                Icon(modifier = Modifier.padding(end = 4.dp, start = 4.dp),
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null)
                            }

                        }
                    }


                }
            }

        }

    }
}





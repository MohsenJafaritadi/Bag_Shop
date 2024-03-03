package com.example.bagshop.ui.features.main

import android.widget.Toast
import com.example.bagshop.R

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.Tags
import com.example.bagshop.model.data.Ads
import com.example.bagshop.model.data.Product
import com.example.bagshop.ui.features.category.CategoryViewModel
import com.example.bagshop.ui.theme.Blue
import com.example.bagshop.ui.theme.CardViewBackground
import com.example.bagshop.ui.theme.Shapes
import com.example.bagshop.util.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MainScreen() {

    val context = LocalContext.current

    val uiController = rememberSystemUiController()



    SideEffect {
        uiController.setStatusBarColor(Color.White)
    }
    val viewmodel = getNavViewModel<MainViewModel>(parameters = { parametersOf(NetworkChecker(context).isInternetConnected) })


    val navigation = getNavController()

    if (NetworkChecker(context).isInternetConnected){
        viewmodel.loadBadgeNumber()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(bottom = 16.dp)
    ) {


        if (viewmodel.showProgressBar.value) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                color = Blue
            )
        }


        TopToolbar(
            badgeNumber = viewmodel.badgeNumber.value,
            onCartClicked = {
            navigation.navigate(MyScreens.CartScreen.route)
        },
            onProfileClicked = {
                navigation.navigate(MyScreens.ProfileScreen.route)
            })


        CategoryBar(CATEGORY) {
            navigation.navigate(MyScreens.CategoryScreen.route + "/" + it)
        }

        val productDataState = viewmodel.dataProduct
        val adsDataState = viewmodel.dataAds


        ProductSubjectList(TAGS, productDataState.value, adsDataState.value) {
            navigation.navigate(MyScreens.ProductScreen.route + "/" + it)
        }


    }

}


@Composable
fun ProductSubjectList(
    tags: List<String>,
    products: List<Product>,
    ads: List<Ads>,
    onProductClicked: (String) -> Unit
) {

    if (products.isNotEmpty()) {

        Column {

            tags.forEachIndexed { it, _ ->

                val withTagData = products.filter { product -> product.tags == tags[it] }
                ProductSubject(tags[it], withTagData.shuffled() , onProductClicked)

                if (ads.size >= 2)
                    if (it == 1 || it == 2)
                        BigPictureTablighat(ads[it - 1] , onProductClicked)

            }

        }

    }

}


@Composable
fun TopToolbar(badgeNumber: Int,onCartClicked: () -> Unit, onProfileClicked: () -> Unit) {

    TopAppBar(

        backgroundColor = Color.White,
        elevation = 0.dp,
        title = { Text(text = "Bag Shop") },
        actions = {
            IconButton(onClick = { onCartClicked.invoke() },
                modifier = Modifier.padding(end = 6.dp)) {

                if (badgeNumber == 0) {
                    Icon(Icons.Default.ShoppingCart, null)
                } else {

                    BadgedBox(badge = { Badge { Text(badgeNumber.toString()) } }) {
                        Icon(Icons.Default.ShoppingCart, null)
                    }
                }
            }
            IconButton(onClick = { onProfileClicked.invoke() }) {
                Icon(Icons.Default.Person, null)
            }
        }
    )

}

//---
@Composable
fun CategoryBar(categoryList: List<Pair<String, Int>>, onCategoryClicked: (String) -> Unit) {

    LazyRow(
        modifier = Modifier.padding(top = 10.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(categoryList.size) {
            CategoryItem(categoryList[it], onCategoryClicked)
        }
    }


}

@Composable
fun CategoryItem(subject: Pair<String, Int>, onCategoryClicked: (String) -> Unit) {

    Column(modifier = Modifier
        .padding(start = 16.dp)
        .clickable { onCategoryClicked.invoke(subject.first) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Surface(
            shape = Shapes.medium,
            color = CardViewBackground
        ) {
            Image(modifier = Modifier.padding(16.dp),
                painter = painterResource(subject.second),
                contentDescription = null)
        }
        Text(text = subject.first,
            modifier = Modifier.padding(top = 4.dp),
            style = TextStyle(color = Color.Gray))

    }


}

//---
@Composable
fun ProductSubject(subject: String, data: List<Product>,onProductClicked: (String) -> Unit,) {

    Column(modifier = Modifier.padding(top = 32.dp)) {

        Text(
            text = subject,
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.h6


        )
        ProductBar(data,onProductClicked)


    }


}

@Composable
fun ProductBar(data: List<Product>,onProductClicked: (String) -> Unit) {


    LazyRow(modifier = Modifier.padding(top = 16.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(data.size) {
            ProductItem(data[it],onProductClicked)
        }
    }


}

@Composable
fun ProductItem(product: Product,onProductClicked: (String) -> Unit) {

    Card(
        modifier = Modifier
            .padding(start = 16.dp)
            .clickable { onProductClicked.invoke(product.productId)},
        elevation = 4.dp,
        shape = Shapes.large
    ) {

        Column {

            AsyncImage(
                model = product.imgUrl,
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop,

                contentDescription = null
            )

            Column(
                modifier = Modifier.padding(10.dp)
            ) {

                Text(
                    text = product.name,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                )

                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = stylePrice(product.price),
                    style = TextStyle(fontSize = 14.sp)
                )

                Text(
                    text = product.soldItem + "Sold",
                    style = TextStyle(color = Color.Gray, fontSize = 13.sp)
                )

            }
        }
    }
}

@Composable
fun BigPictureTablighat(ads: Ads,onProductClicked: (String) -> Unit,) {
    AsyncImage(
        modifier = Modifier
            .fillMaxSize()
            .height(260.dp)
            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
            .clip(Shapes.medium)
            .clickable { onProductClicked.invoke(ads.productId)},


        model = ads.imageURL,

        contentDescription = null,
        contentScale = ContentScale.Crop)
}





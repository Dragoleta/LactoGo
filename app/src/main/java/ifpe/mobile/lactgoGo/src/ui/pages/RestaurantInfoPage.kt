package ifpe.mobile.lactgoGo.src.ui.pages

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ifpe.mobile.lactgoGo.src.MVM.MainViewModel
import ifpe.mobile.lactgoGo.src.ui.composables.DishIndoCard
import ifpe.mobile.lactgoGo.src.ui.composables.RestaurantInfoCard

@Composable
fun RestaurantInfoPage(
    context: Context,
    modifier :Modifier,
    viewModel : MainViewModel
) {
    val restaurant = viewModel.restaurant
    val dishes = restaurant.menu


    Column (
        modifier.padding(top = 15.dp, bottom = 30.dp, start = 20.dp, end=20.dp)
    ) {

        RestaurantInfoCard( modifier = modifier, restaurant = restaurant)

        HorizontalDivider(modifier.height(15.dp), color = Color.Transparent)
        LazyColumn(
            modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(dishes) { dish -> DishIndoCard(modifier = modifier, dish = dish) }
        }
    }



}
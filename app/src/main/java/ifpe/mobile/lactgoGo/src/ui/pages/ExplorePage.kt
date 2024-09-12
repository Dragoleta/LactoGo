package ifpe.mobile.lactgoGo.src.ui.pages

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ifpe.mobile.lactgoGo.src.MVM.MainViewModel
import ifpe.mobile.lactgoGo.src.database.models.RestaurantModel


@Composable
fun PlaceItemCard(
    place: RestaurantModel,
    onPin: () -> Unit,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp, 8.dp, 8.dp, 8.dp)
            .clickable { onSelect() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.size(12.dp))
        Column(modifier = modifier.weight(1f)) {
            Text(
                modifier = Modifier,
                text = place.name,
                fontSize = 24.sp
            )
            Text(
                modifier = Modifier,
                text = place.address,
                fontSize = 16.sp
            )
            Text(
                modifier = Modifier,
                text = "${place.openingTime}",
                fontSize = 16.sp
            )

        }
        IconButton(onClick = onPin) {
            Icon(Icons.Filled.Place, contentDescription = "Close")
        }
    }
}

@Composable
fun ExplorePageComp(modifier: Modifier = Modifier, viewModel: MainViewModel, context: Context, navController: NavController) {
    var address by rememberSaveable { mutableStateOf("") }
    val restaurantList by remember { mutableStateOf(viewModel.restaurants) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,
    ) {
        OutlinedTextField(
            value = address, onValueChange = { address = it }, shape = RoundedCornerShape(20.dp),
            label = { Text(text = "Address") },
            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 8.dp)
                .fillMaxWidth(),
        )

        Spacer(modifier = modifier.size(50.dp))

        ElevatedButton(onClick = { navController.navigate("register") }) {
            Text(text = "Add restaurant")
        }
        
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            println("Banana $restaurantList")
            items(restaurantList) {
                restaurant -> PlaceItemCard(
                place = restaurant,
                onSelect = {
                    Toast.makeText(context, "Opening ${restaurant.name}", Toast.LENGTH_LONG).show()
                },
                onPin = {
                    Toast.makeText(context, "Place pinned", Toast.LENGTH_LONG).show()
                },
            )
            }

        }
    }
}
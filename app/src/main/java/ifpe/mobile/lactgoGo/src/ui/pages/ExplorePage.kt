package ifpe.mobile.lactgoGo.src.ui.pages

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ifpe.mobile.lactgoGo.src.MVM.MainViewModel
import ifpe.mobile.lactgoGo.src.database.models.RestaurantModel
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color


@Composable
fun PlaceItemCard(
    place: RestaurantModel,
    onPin: () -> Unit,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    place.address?.street

    Row(
        modifier
            .fillMaxWidth()
            .padding(8.dp, 8.dp, 8.dp, 8.dp)
            .background(color = Color.Gray)
            .clickable { onSelect() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = modifier.size(12.dp))
        Column(modifier = modifier.weight(1f)) {
            Text(
                modifier = modifier,
                text = place.name,
                fontSize = 24.sp
            )
            Text(
                modifier = modifier,
                text = "${place.address?.street}, ${place.address?.number} - ${place.address?.district}",
                fontSize = 16.sp
            )
            Text(
                modifier = modifier,
                text = "${place.openingTime} - ${place.closingTime}",
                fontSize = 16.sp
            )

        }
        IconButton(onClick = onPin) {
            Icon(Icons.Filled.Place, contentDescription = "Close")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExplorePageComp(modifier: Modifier = Modifier, viewModel: MainViewModel, context: Context, navController: NavController) {
    var address by rememberSaveable { mutableStateOf("") }
    val restaurantList by remember { mutableStateOf(viewModel.restaurants) }
    val activity = LocalContext.current as? Activity

    // Variáveis para controlar o menu
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = CenterHorizontally,
    ) {
        // TopAppBar com menu
        TopAppBar(
            title = { Text("Explorar Restaurantes") },
            actions = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "Menu")
                }
                // Menu suspenso
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(onClick = {
                        navController.navigate("edit-profile") // Navegar para EditProfileActivity
                        expanded = false // Fechar o menu
                    }) {
                        Text("Editar Perfil")
                    }
                    // Você pode adicionar mais itens de menu aqui se necessário
                }
            },
        )

        OutlinedTextField(
            value = address, onValueChange = { address = it }, shape = RoundedCornerShape(20.dp),
            label = { Text(text = "Endereço") },
            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 8.dp)
                .fillMaxWidth(),
        )

        Spacer(modifier = modifier.size(50.dp))

        ElevatedButton(onClick = { navController.navigate("register") }) {
            Text(text = "Adicionar restaurante")
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            items(restaurantList) { restaurant ->
                PlaceItemCard(
                    place = restaurant,
                    onSelect = {
                        viewModel.setRestaurant(rests = restaurant)
                        navController.navigate("rest-info")
                    },
                    onPin = {
                        activity?.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("http://maps.google.com/maps/dir/?api=1" +
                                        "&destination=${restaurant.name}, ${restaurant.address?.city}, ${restaurant.address?.country}")
                            ).setFlags(
                                FLAG_ACTIVITY_SINGLE_TOP
                            )
                        )
                        Toast.makeText(context, "Lugar fixado", Toast.LENGTH_LONG).show()
                    }
                )
            }
        }
    }
}

fun DropdownMenuItem(onClick: () -> Unit, interactionSource: @Composable () -> Unit) {

}

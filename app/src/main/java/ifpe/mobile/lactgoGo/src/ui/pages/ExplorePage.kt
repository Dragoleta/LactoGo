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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
            .padding(8.dp)
            .background(color = Color(0xFFF1F5F4), shape = RoundedCornerShape(16.dp))
            .clickable { onSelect() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f).padding(start = 12.dp)) {
            Text(
                text = place.name,
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = Color(0xFF717F7F)
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${place.address?.street}, ${place.address?.number} - ${place.address?.district}",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color(0xFFA0A0A0)
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${place.openingTime} - ${place.closingTime}",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color(0xFFA0A0A0)
                )
            )
        }
        IconButton(onClick = onPin) {
            Icon(Icons.Filled.Place, contentDescription = "Pin Location", tint = Color(0xFF1C349B))
        }
    }
}

@Composable
fun ExplorePageComp(
    viewModel: MainViewModel,
    context: Context,
    navController: NavController
) {
    var address by rememberSaveable { mutableStateOf("") }
    val activity: Activity? = LocalContext.current as? Activity
    val restaurantList by remember { mutableStateOf(viewModel.restaurants) }
    var restaurantToShow by remember { mutableStateOf(restaurantList) }


    Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFFFFF))
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Campo de Pesquisa de Endereço
            OutlinedTextField(
                value = address,
                onValueChange =  {
                    address = it
                    restaurantToShow = restaurantList.filter { restaurant -> restaurant.name.contains(address) }
                },
                shape = RoundedCornerShape(20.dp),
                label = { Text(text = "Filter by name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Botão para Adicionar Restaurante
            ElevatedButton(
                onClick = { navController.navigate("register") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.elevatedButtonColors(containerColor = Color(0xFF1C349B))
            ) {
                Text(
                    text = "Adicionar restaurante",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }

            // Lista de Locais
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(restaurantToShow ) {
                        restaurant ->
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
                                    Uri.parse(
                                        "http://maps.google.com/maps/dir/?api=1" +
                                                "&destination=${restaurant.name}, ${restaurant.address?.city}, ${restaurant.address?.country}"
                                    )
                                ).setFlags(
                                    FLAG_ACTIVITY_SINGLE_TOP
                                )
                            )
                            Toast.makeText(context, "Place pinned", Toast.LENGTH_LONG)
                                .show()
                        },

                    )
                }
            }
        }
    }

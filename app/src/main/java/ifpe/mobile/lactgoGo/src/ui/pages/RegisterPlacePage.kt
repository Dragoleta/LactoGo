package ifpe.mobile.lactgoGo.src.ui.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ifpe.mobile.lactgoGo.R
import ifpe.mobile.lactgoGo.src.database.db.FirebaseDB
import ifpe.mobile.lactgoGo.src.database.models.AddressModel
import ifpe.mobile.lactgoGo.src.database.models.RestaurantModel


@Composable
fun RegisterPlacePage(modifier: Modifier = Modifier, database : FirebaseDB, navController: NavHostController) {
    val (pageNumber, setPageNumber) = rememberSaveable { mutableIntStateOf(1) }
    val (restaurantToAdd, setRestaurant) = remember {    mutableStateOf(RestaurantModel()) }
    val (addressInfo, setAddressInfo) = remember {    mutableStateOf(AddressModel()) }

    Log.d("RegisterPlaceForm", "Current Restaurant: $restaurantToAdd")

    val pageContent = when (pageNumber)   {
        1 -> RegisterPlaceForm(
            modifier = modifier,
            restaurantToAdd = restaurantToAdd,
            onRestaurantChange = { updatedRestaurant ->
                setRestaurant(updatedRestaurant)
            },
            onContinueClick = {
                setPageNumber(2)
            }
        )
        2 -> RegisterPlacePage2(
            modifier = modifier,
            addressInfo = addressInfo,
            onAddressChange = { updatedAddress ->
                setAddressInfo(updatedAddress)
                setRestaurant(restaurantToAdd.copy(address = addressInfo))

            },
            onContinueClick = {
                Log.d("RegisterPlaceForm", "Current Restaurant : $restaurantToAdd")
                database.saveRestaurant(restaurantToAdd)
                // TODO: Add check before returning to main
                database.getRestaurants()
                navController.popBackStack()

            } )
        else -> {Text("Error!")}
    }
}


@Composable
fun RegisterPlaceForm(
    modifier: Modifier = Modifier,
    restaurantToAdd: RestaurantModel,
    onRestaurantChange: (RestaurantModel) -> Unit,
    onContinueClick: () -> Unit
) {

    var businessName by rememberSaveable { mutableStateOf("") }
    var openingTime by rememberSaveable { mutableStateOf("") }
    var closingTime by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_login),
            contentDescription = "Novo Local",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .padding(top = 32.dp)
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = "Novo Local",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1C349B),
                textAlign = TextAlign.Center,
            )
        )

        Spacer(modifier = Modifier.size(40.dp))

        TextField(
            value =  businessName,
            onValueChange = {
                onRestaurantChange(restaurantToAdd.copy(name = it))
                businessName = it
            },
            label = { Text(text = "Nome da Empresa") },
            modifier = Modifier
                .width(323.dp)
                .height(55.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color(0xFFF1F5F4), shape = RoundedCornerShape(size = 8.dp))
        )

        Spacer(modifier = Modifier.size(8.dp))

        TextField(
            value = openingTime, onValueChange = {
                openingTime = it
                onRestaurantChange(restaurantToAdd.copy(openingTime = it.toInt()))
            },
            label = { Text(text = "Horario de abertura") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),

            modifier = Modifier
                .width(323.dp)
                .height(55.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color(0xFFF1F5F4), shape = RoundedCornerShape(size = 8.dp))
        )

        Spacer(modifier = Modifier.size(8.dp))

        TextField(
            value =  closingTime, onValueChange = {
                closingTime = it
                onRestaurantChange(restaurantToAdd.copy(closingTime = it.toInt()))
            },
            label = { Text(text = "Horário de fechamento") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),

            modifier = Modifier
                .width(323.dp)
                .height(55.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color(0xFFF1F5F4), shape = RoundedCornerShape(size = 8.dp))
        )

        Spacer(modifier = Modifier.size(8.dp))

       TextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
                onRestaurantChange(restaurantToAdd.copy(contacts = listOf(it)))
            },
            label = { Text(text = "Contato") },

            modifier = Modifier
                .width(323.dp)
                .height(55.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color(0xFFF1F5F4), shape = RoundedCornerShape(size = 8.dp))
       )

        Spacer(modifier.size(35.dp))

        Button(
            onClick = {
                onContinueClick()
            },

            modifier
                .width(323.dp)
                .height(45.dp)
                .clip(RoundedCornerShape(16.dp)),
            enabled = phoneNumber.isNotEmpty() && businessName.isNotEmpty() && openingTime.isNotEmpty() && phoneNumber.isNotEmpty()
        ) {
            Text(
                text = "Próximo",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                     )
            )
        }
    }
}

@Composable
fun RegisterPlacePage2(
    modifier: Modifier = Modifier,
    addressInfo: AddressModel,
    onAddressChange: (AddressModel) -> Unit,
    onContinueClick: () -> Unit
) {
    var street by rememberSaveable { mutableStateOf("") }
    var number by rememberSaveable { mutableStateOf("") }
    var district by rememberSaveable { mutableStateOf("") }
    var city by rememberSaveable { mutableStateOf("") }
    var state by rememberSaveable { mutableStateOf("") }
    var country by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,
    ) {

        Text(
            text = "Endereço do Local",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1C349B),
                textAlign = TextAlign.Center,
            )
        )

        Spacer(modifier = Modifier.size(40.dp))

        TextField(
            value = street,
            onValueChange = {
                street = it
                onAddressChange(addressInfo.copy(street = street))
            },
            label = { Text(text = "Nome da rua") },
            modifier = Modifier
                .width(323.dp)
                .height(55.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color(0xFFF1F5F4), shape = RoundedCornerShape(size = 8.dp))
        )

        Spacer(modifier = Modifier.size(8.dp))

        TextField(
            value = number,
            onValueChange = {
                number = it
                onAddressChange(addressInfo.copy(number = number.toInt()))
            },
            label = { Text(text = "CEP") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .width(323.dp)
                .height(55.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color(0xFFF1F5F4), shape = RoundedCornerShape(size = 8.dp))
        )

        Spacer(modifier = Modifier.size(8.dp))

        TextField(
            value = district,
            onValueChange = {
                district = it
                onAddressChange(addressInfo.copy(district = district))
            },
            label = { Text(text = "Bairro") },
            modifier = Modifier
                .width(323.dp)
                .height(55.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color(0xFFF1F5F4), shape = RoundedCornerShape(size = 8.dp))
        )

        Spacer(modifier = Modifier.size(8.dp))

        TextField(
            value = city,
            onValueChange = {
                city = it
                onAddressChange(addressInfo.copy(city = city))
            },
            label = { Text(text = "Cidade") },
            modifier = Modifier
                .width(323.dp)
                .height(55.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color(0xFFF1F5F4), shape = RoundedCornerShape(size = 8.dp))
        )

        Spacer(modifier = Modifier.size(8.dp))

        TextField(
            value = state,
            onValueChange = {
                state = it
                onAddressChange(addressInfo.copy(state = state))
            },
            label = { Text(text = "Estado") },
            modifier = Modifier
                .width(323.dp)
                .height(55.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color(0xFFF1F5F4), shape = RoundedCornerShape(size = 8.dp))
        )

        Spacer(modifier = Modifier.size(8.dp))

        TextField(
            value = country,
            onValueChange = {
                country = it
                onAddressChange(addressInfo.copy(country = country))
            },
            label = { Text(text = "País") },
            modifier = Modifier
                .width(323.dp)
                .height(55.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color(0xFFF1F5F4), shape = RoundedCornerShape(size = 8.dp))
        )

        Spacer(modifier = Modifier.size(35.dp))

        Button(
            onClick = {
                onContinueClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .size(85.dp)
                .padding(16.dp, 16.dp, 16.dp, 16.dp),
            enabled = street.isNotEmpty() && number.isNotEmpty() && district.isNotEmpty() && city.isNotEmpty()
        ) {
            Text(
                text = "Registrar",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}
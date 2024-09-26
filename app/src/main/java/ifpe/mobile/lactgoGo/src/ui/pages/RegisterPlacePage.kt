 package ifpe.mobile.lactgoGo.src.ui.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
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

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,
    ) {

        Text(
            text = "Novo Local",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.size(75.dp))

        OutlinedTextField(
            value =  businessName,
            onValueChange = {
                onRestaurantChange(restaurantToAdd.copy(name = it))
                businessName = it
                },
            shape = RoundedCornerShape(20.dp),
            label = { Text(text = "Nome da Empresa") },
            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 8.dp)
                .fillMaxWidth(),
        )
        OutlinedTextField(
            value = openingTime, onValueChange = {
                openingTime = it
                onRestaurantChange(restaurantToAdd.copy(openingTime = it.toInt()))
                                                 },
            shape = RoundedCornerShape(20.dp),
            label = { Text(text = "Horario de abertura") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),

            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 8.dp)
                .fillMaxWidth(),
        )
        OutlinedTextField(
            value =  closingTime, onValueChange = {
                closingTime = it
                onRestaurantChange(restaurantToAdd.copy(closingTime = it.toInt()))
              },
            shape = RoundedCornerShape(20.dp),
            label = { Text(text = "Horario de fechamento") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),

            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 8.dp)
                .fillMaxWidth(),
        )
        
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
                onRestaurantChange(restaurantToAdd.copy(contacts = listOf(it)))
                },
            shape = RoundedCornerShape(20.dp),
            label = { Text(text = "Contato") },

            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 8.dp)
                .fillMaxWidth(),
        )

        Spacer(modifier.size(35.dp))

        Button(
            onClick = {
                onContinueClick()
              },

            modifier
                .fillMaxWidth()
                .size(85.dp)
                .padding(16.dp, 16.dp, 16.dp, 16.dp),
            enabled = phoneNumber.isNotEmpty() && businessName.isNotEmpty() && openingTime.isNotEmpty() && phoneNumber.isNotEmpty()
        ) {
            Text(text = "Next")
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
             text = "Address info",
             fontSize = 24.sp
         )

         Spacer(modifier = Modifier.size(75.dp))

         OutlinedTextField(
             value =  street,
             onValueChange = {
                 street = it
                 onAddressChange(addressInfo.copy(street = street))
            },
             shape = RoundedCornerShape(20.dp),
             label = { Text(text = "Street name") },

             modifier = Modifier
                 .padding(16.dp, 4.dp, 16.dp, 4.dp)
                 .fillMaxWidth(),
         )
         Spacer(modifier.size(8.dp))
         Row(
             modifier = modifier.fillMaxWidth(),
             horizontalArrangement = Arrangement.Center
         ) {
             OutlinedTextField(
                 value = number,
                 onValueChange = {
                     number = it
                     onAddressChange(addressInfo.copy( number= number.toInt()))

                 },
                 shape = RoundedCornerShape(20.dp),
                 label = { Text(text = "Number") },
                 keyboardOptions = KeyboardOptions.Default.copy(
                     keyboardType = KeyboardType.Number
                 ),

                 modifier = Modifier
                     .padding(16.dp, 4.dp, 8.dp, 4.dp)
                     .weight(5f),
             )
             OutlinedTextField(
                 value = district,
                 onValueChange = {
                     district = it
                     onAddressChange(addressInfo.copy( district = district))

                 },
                 shape = RoundedCornerShape(20.dp),
                 label = { Text(text = "District") },

                 modifier = Modifier
                     .padding(8.dp, 4.dp, 16.dp, 4.dp)
                     .weight(10f),
             )
         }
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
             OutlinedTextField(
                value = city,
                onValueChange = {
                    city = it
                    onAddressChange(addressInfo.copy( city = city))
                },
                shape = RoundedCornerShape(20.dp),
                label = { Text(text = "city") },
                modifier = Modifier
                    .padding(16.dp, 8.dp, 8.dp, 8.dp)
                    .weight(10f),
            )
             OutlinedTextField(
                 value = state,
                 onValueChange = {
                     state = it
                     onAddressChange(addressInfo.copy(state = state))
                 },
                 shape = RoundedCornerShape(20.dp),
                 label = { Text(text = "state") },
                 modifier = Modifier
                     .padding(8.dp, 8.dp, 16.dp, 8.dp)
                     .weight(7f),
         )
        }

         OutlinedTextField(
         value = country,
         onValueChange = {
             country = it
             onAddressChange(addressInfo.copy(country  = country))
          },
         shape = RoundedCornerShape(20.dp),
         label = { Text(text = "country") },
         modifier = Modifier
             .padding(16.dp, 8.dp, 16.dp, 8.dp)
             .fillMaxWidth(),
         )

         Spacer(modifier.size(35.dp))

         Button(
             onClick = {

                 onContinueClick()
             },
             modifier
                 .fillMaxWidth()
                 .size(85.dp)
                 .padding(16.dp, 16.dp, 16.dp, 16.dp),

             enabled = street.isNotEmpty() && number.isNotEmpty() && district.isNotEmpty() && city.isNotEmpty()
         ) {
             Text(text = "Register")
         }
     }
 }

// fun callAPI() {
//     // Initialize OkHttpClient
//     val client = OkHttpClient()
//
//     // Create the request
//     val request = Request.Builder()
//         .url("https://viacep.com.br/ws/50810300/json/") // Replace with the desired CEP
//         .build()
//
//     // Execute the request
//     client.newCall(request).enqueue(object : okhttp3.Callback {
//         override fun onFailure(call: Call, e: IOException) {
//             e.printStackTrace()
//         }
//
//         override fun onResponse(call: Call, response: Response) {
//             if (response.isSuccessful) {
//                 response.body?.let { responseBody ->
//                     val responseData = responseBody.to()
//
//
//                     val gson = Gson()
//                     try {
//                         val address = gson.fromJson(responseData, AddressAPI::class.java)
//                         Log.d("CEPCALL", "Current Restaurant: $address")
//
//                     } catch (e: JsonSyntaxException) {
//                         e.printStackTrace()
//                     }
//
//                 }
//             } else {
//                 println("Banana Request failed with status code: ${response.code}")
//             }
//         }
//     })
// }
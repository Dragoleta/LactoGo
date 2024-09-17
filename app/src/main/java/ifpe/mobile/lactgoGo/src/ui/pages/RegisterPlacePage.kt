 package ifpe.mobile.lactgoGo.src.ui.pages

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ifpe.mobile.lactgoGo.src.MVM.MainViewModel
import ifpe.mobile.lactgoGo.src.database.db.FirebaseDB
import ifpe.mobile.lactgoGo.src.database.models.RestaurantModel


 @Composable
fun RegisterPlacePage(modifier: Modifier = Modifier, database : FirebaseDB, viewModel: MainViewModel, context: Context) {

     var businessName by rememberSaveable { mutableStateOf("") }
     var address by rememberSaveable { mutableStateOf("") }
     var openingTime by rememberSaveable { mutableStateOf("") }
     var closingTime by rememberSaveable { mutableStateOf("") }
     var phoneNumber by rememberSaveable { mutableStateOf("") }
    val newRestaurant = RestaurantModel()

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
            onValueChange = { businessName = it },
            shape = RoundedCornerShape(20.dp),
            label = { Text(text = "Nome da Empresa") },

            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 8.dp)
                .fillMaxWidth(),
        )

        OutlinedTextField(
            value = address, onValueChange = { address = it }, shape = RoundedCornerShape(20.dp),
            label = { Text(text = "Enderecxo") },

            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 8.dp)
                .fillMaxWidth(),
        )

        OutlinedTextField(
            value = openingTime, onValueChange = { openingTime = it }, shape = RoundedCornerShape(20.dp),
            label = { Text(text = "Horario de abertura") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),

            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 8.dp)
                .fillMaxWidth(),
        )

        OutlinedTextField(
            value =  closingTime, onValueChange = { closingTime = it }, shape = RoundedCornerShape(20.dp),
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
            onValueChange = { phoneNumber = it },
            shape = RoundedCornerShape(20.dp),
            label = { Text(text = "Contato") },

            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 8.dp)
                .fillMaxWidth(),
        )

        Spacer(modifier.size(35.dp))

        Button(
            onClick = {
                newRestaurant.contacts = listOf(phoneNumber)
                newRestaurant.address = address
                newRestaurant.closingTime = closingTime.toInt()
                newRestaurant.openingTime = openingTime.toInt()
                newRestaurant.contacts = listOf(phoneNumber)
                newRestaurant.name = businessName
                database.saveRestaurant(newRestaurant)
              },

            modifier
                .fillMaxWidth()
                .size(85.dp)
                .padding(16.dp, 16.dp, 16.dp, 16.dp),
            enabled = phoneNumber.isNotEmpty() && businessName.isNotEmpty() && address.isNotEmpty()
        ) {
            Text(text = "Cadastrar")
        }
    }
}
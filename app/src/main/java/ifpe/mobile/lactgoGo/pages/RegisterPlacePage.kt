package ifpe.mobile.lactgoGo.pages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ifpe.mobile.lactgoGo.ui.theme.MyApplicationTheme

class RegisterPlacePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegisterPlaceComp()
                }
            }
        }
    }
}


@Composable
fun RegisterPlaceComp(modifier: Modifier = Modifier) {

    var businessName by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var district by rememberSaveable { mutableStateOf("") }
    var workHours by rememberSaveable { mutableStateOf("") }
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
            value = businessName,
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
            value = district, onValueChange = { district = it }, shape = RoundedCornerShape(20.dp),
            label = { Text(text = "Bairro") },

            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 8.dp)
                .fillMaxWidth(),
        )
        OutlinedTextField(
            value = workHours,
            onValueChange = { workHours = it },
            shape = RoundedCornerShape(20.dp),
            label = { Text(text = "Horario de funcionamento") },

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

        Spacer(modifier = Modifier.size(35.dp))

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .size(85.dp)
                .padding(16.dp, 16.dp, 16.dp, 16.dp),
            enabled = phoneNumber.isNotEmpty() && businessName.isNotEmpty() && workHours.isNotEmpty() && address.isNotEmpty() && district.isNotEmpty()
        ) {
            Text(text = "Cadastrar")
        }
    }
}
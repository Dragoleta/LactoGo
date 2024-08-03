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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ifpe.mobile.lactgoGo.ui.theme.MyApplicationTheme

class RegisterPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegisterPageComp()
                }
            }
        }
    }
}


@Composable
fun RegisterPageComp(modifier: Modifier = Modifier) {
    var name by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordConf by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,
    ) {

        Text(
            text = "Cadastro",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.size(100.dp))

        OutlinedTextField(
            value = name, onValueChange = { name = it }, shape = RoundedCornerShape(20.dp),
            label = { Text(text = "Nome") },

            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 8.dp)
                .fillMaxWidth(),
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            shape = RoundedCornerShape(20.dp),
            label = { Text(text = "Email") },

            modifier = Modifier
                .padding(16.dp, 8.dp, 16.dp, 8.dp)
                .fillMaxWidth(),
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            shape = RoundedCornerShape(20.dp),
            label = { Text(text = "Senha ") },

            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .padding(16.dp, 8.dp, 16.dp, 8.dp)
                .fillMaxWidth(),
        )
        OutlinedTextField(
            value = passwordConf,
            label = { Text(text = "Confirme sua senha") },
            onValueChange = { passwordConf = it },
            shape = RoundedCornerShape(20.dp),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .padding(16.dp, 8.dp, 16.dp, 16.dp)
                .fillMaxWidth(),
        )

        Spacer(modifier = Modifier.size(35.dp))

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp, 16.dp, 16.dp),
            enabled = name.isNotEmpty() && email.isNotEmpty() && password == passwordConf
        ) {
            Text(text = "Cadastrar")
        }

    }
}




























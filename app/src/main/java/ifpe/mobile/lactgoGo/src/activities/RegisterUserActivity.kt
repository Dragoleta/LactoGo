package ifpe.mobile.lactgoGo.src.activities

import User
import android.app.Activity
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import ifpe.mobile.lactgoGo.src.ui.theme.MyApplicationTheme
import ifpe.mobile.lactgoGo.src.database.db.FirebaseDB

class RegisterUserActivity : ComponentActivity() {
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
fun RegisterPageComp() {
    var name by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordConf by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    val activity = LocalContext.current as? Activity
    val fbDB = remember { FirebaseDB() }

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp, 16.dp, 16.dp),
            enabled = name.isNotEmpty() && email.isNotEmpty() && password == passwordConf,
            onClick = {
                Firebase.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity!!) { task ->
                    if (task.isSuccessful) {
                        fbDB.register(User(name = name, email = email))
                        Toast.makeText(activity,
                            "Registro OK!", Toast.LENGTH_LONG).show()
                        activity.finish()
                    } else {
                        Toast.makeText(activity,
                            "Registro FALHOU!", Toast.LENGTH_LONG).show()
                    }
                } }
        ) {
            Text(text = "Cadastrar")
        }

    }
}

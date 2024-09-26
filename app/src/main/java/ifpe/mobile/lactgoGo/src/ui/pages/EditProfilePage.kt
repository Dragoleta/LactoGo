package ifpe.mobile.lactgoGo.src.ui.pages

import User
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import ifpe.mobile.lactgoGo.src.MVM.MainViewModel
import ifpe.mobile.lactgoGo.src.database.db.FirebaseDB


@Composable
fun EditProfilePage(
    viewModel: MainViewModel,
    database: FirebaseDB,
    navController: NavHostController
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordConf by rememberSaveable { mutableStateOf("") }
    val activity = LocalContext.current as? Activity
    val fbDB = remember { FirebaseDB() }
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Button(
            onClick = {
                val user = auth.currentUser
                if (user != null) {
                    user.updateEmail(email).addOnCompleteListener(activity!!) { task ->
                        if (task.isSuccessful) {
                            if (password.isNotEmpty()) {
                                user.updatePassword(password).addOnCompleteListener { passwordTask ->
                                    if (passwordTask.isSuccessful) {
                                        fbDB.updateUser(User(name = name, email = email)) // Atualiza o usuário no banco de dados
                                        Toast.makeText(activity, "Perfil atualizado com sucesso!", Toast.LENGTH_LONG).show()
                                        navController.navigate("home")  // Navega para a tela principal após atualização
                                    } else {
                                        Toast.makeText(activity, "Falha ao atualizar a senha!", Toast.LENGTH_LONG).show()
                                    }
                                }
                            } else {
                                fbDB.updateUser(User(name = name, email = email))
                                Toast.makeText(activity, "Perfil atualizado com sucesso!", Toast.LENGTH_LONG).show()
                                navController.navigate("home")  // Navega para a tela principal após atualização
                            }
                        } else {
                            Toast.makeText(activity, "Falha ao atualizar o e-mail!", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            },
            enabled = name.isNotEmpty() && email.isNotEmpty() && password == passwordConf
        ) {
            Text(text = "Atualizar")
        }
    }
}
package ifpe.mobile.lactgoGo.src.ui.pages

import User
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import ifpe.mobile.lactgoGo.R
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
    var oldPassword by rememberSaveable { mutableStateOf("") }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    val selectedRestrictions = remember { mutableStateListOf<String>() }
    val restrictions = listOf(
        "Intolerância à lactose",
        "Intolerância ao glúten",
        "Alergia a amendoim",
        "Alergia a frutos do mar",
        "Diabetes"
    )
    val activity = LocalContext.current as? Activity
    val fbDB = remember { FirebaseDB() }
    val scrollState = rememberScrollState()
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    if (currentUser == null) {
        LaunchedEffect(Unit) {
            navController.navigate("login") {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
        return
    }

    LaunchedEffect(Unit) {
        fbDB.getUser { user ->
            user?.let {
                name = it.name
                email = it.email
                it.restrictions?.let { it1 -> selectedRestrictions.addAll(it1) }
            }
        }
    }


    Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_login),
                contentDescription = "Edit User Icon",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(top = 32.dp)
            )

        Spacer(modifier = Modifier.height(54.dp))

        Text(
            text = "Editar Usuário",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1C349B),
                textAlign = TextAlign.Center,
            )
        )

        Spacer(modifier = Modifier.height(54.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Nome") },
            modifier = Modifier
                .width(323.dp)
                .height(55.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color(0xFFF1F5F4), shape = RoundedCornerShape(size = 8.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "E-mail") },
            modifier = Modifier
                .width(323.dp)
                .height(55.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color(0xFFF1F5F4), shape = RoundedCornerShape(size = 8.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = oldPassword,
            onValueChange = { oldPassword = it },
            label = { Text(text = "Senha Antiga") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .width(323.dp)
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text(text = "Nova Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .width(323.dp)
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text(text = "Confirme a Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .width(323.dp)
                .height(55.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Selecione suas restrições alimentares",
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1C349B)),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        restrictions.forEach { restriction ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable {
                        if (selectedRestrictions.contains(restriction)) {
                            selectedRestrictions.remove(restriction)
                        } else {
                            selectedRestrictions.add(restriction)
                        }
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedRestrictions.contains(restriction),
                    onCheckedChange = { checked ->
                        if (checked) {
                            selectedRestrictions.add(restriction)
                        } else {
                            selectedRestrictions.remove(restriction)
                        }
                    }
                )
                Text(text = restriction, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .width(323.dp)
                .height(45.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color(0xFF1C349B)),
            onClick = {
                val user = User(name = name, email = email, restrictions = selectedRestrictions.toList())
                fbDB.updateUser(user)

                // Verifica se a nova senha é diferente da antiga antes de atualizar
                if (newPassword.isNotEmpty() && newPassword == confirmPassword) {
                    fbDB.reauthenticateUser(email, oldPassword) { success ->
                        if (success) {
                            fbDB.updatePassword(newPassword) { passwordUpdateSuccess ->
                                if (passwordUpdateSuccess) {
                                    Toast.makeText(activity, "Usuário atualizado com sucesso!", Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(activity, "Falha ao atualizar a senha!", Toast.LENGTH_LONG).show()
                                }
                            }
                        } else {
                            Toast.makeText(activity, "Senha antiga incorreta!", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(activity, "Nova senha e confirmação não coincidem!", Toast.LENGTH_LONG).show()
                }

                activity?.finish() // Finaliza a atividade atual após a atualização
            }
        ) {
            Text(
                text = "Salvar",
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
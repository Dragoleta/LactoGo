package ifpe.mobile.lactgoGo.src.activities

import User
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import ifpe.mobile.lactgoGo.R
import ifpe.mobile.lactgoGo.src.database.db.FirebaseDB
import ifpe.mobile.lactgoGo.src.ui.composables.ClickableText
import ifpe.mobile.lactgoGo.src.ui.theme.MyApplicationTheme

class RegisterUserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
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
    var drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
                Text("Menu", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Item 1", modifier = Modifier.clickable { /* Ação 1 */ })
                Text("Item 2", modifier = Modifier.clickable { /* Ação 2 */ })
                Text("Item 3", modifier = Modifier.clickable { /* Ação 3 */ })
            }
        }
    ) {
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
                contentDescription = "Login Icon",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(top = 32.dp)
            )

            Spacer(modifier = Modifier.height(54.dp))

            Text(
                text = "Cadastro",
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
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                label = { Text(text = "Senha") },
                modifier = Modifier
                    .width(323.dp)
                    .height(55.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = Color(0xFFF1F5F4), shape = RoundedCornerShape(size = 8.dp),)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = passwordConf,
                onValueChange = { passwordConf = it },
                visualTransformation = PasswordVisualTransformation(),
                label = { Text(text = "Confirme sua senha") },
                modifier = Modifier
                    .width(323.dp)
                    .height(55.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = Color(0xFFF1F5F4), shape = RoundedCornerShape(size = 8.dp),)
            )

            Spacer(modifier = Modifier.height(56.dp))

            Text(
                text = "Selecione suas restrições alimentares",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1C349B), textAlign = TextAlign.Center),
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
                    .background(
                        color = Color(0xFF1C349B),
                        shape = RoundedCornerShape(size = 8.dp)
                    ),
                enabled = name.isNotEmpty() && email.isNotEmpty() && password == passwordConf && selectedRestrictions.isNotEmpty(),
                onClick = {
                    Firebase.auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity!!) { task ->
                            if (task.isSuccessful) {
                                fbDB.register(
                                    User(
                                        name = name,
                                        email = email,
                                        restrictions = selectedRestrictions.toList()
                                    )
                                )
                                Toast.makeText(
                                    activity,
                                    "Registro OK!", Toast.LENGTH_LONG
                                ).show()
                                activity.startActivity(Intent(activity, LoginActivity::class.java))
                                activity.finish()
                            } else {
                                Toast.makeText(
                                    activity,
                                    "Registro FALHOU!", Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }
            ) {
                Text(
                    text = "Cadastrar",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            ClickableText(
                text = "Já possui conta? Entre",
                onClick = {
                    activity?.startActivity(
                        Intent(activity, LoginActivity::class.java).setFlags(
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
                        )
                    )
                }
            )
        }
    }
}

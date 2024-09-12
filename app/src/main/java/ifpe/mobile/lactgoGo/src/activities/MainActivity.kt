package ifpe.mobile.lactgoGo.src.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import ifpe.mobile.lactgoGo.src.ui.pages.ExplorePageComp
import ifpe.mobile.lactgoGo.src.ui.theme.MyApplicationTheme
import androidx.compose.foundation.layout.Box

import android.Manifest
import androidx.activity.viewModels
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import ifpe.mobile.lactgoGo.src.MVM.MainViewModel
import ifpe.mobile.lactgoGo.src.database.db.FirebaseDB
import ifpe.mobile.lactgoGo.src.ui.navegation.BottomNavBar
import ifpe.mobile.lactgoGo.src.ui.navegation.MainNavHost

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(), onResult = {} )
            val navController = rememberNavController()
            val context = LocalContext.current
            val viewModel : MainViewModel by viewModels()
            val fbDB = remember { FirebaseDB(viewModel) }

            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                           title = { Text("LactoGo") },
                            navigationIcon = {
                                NavigationIcon(
                                    icon = Icons.Default.Menu,
                                    onClick = { /* Handle navigation icon click */ }
                                )
                            },
                        )
                    },
                    bottomBar = {
                        BottomNavBar(navController = navController)
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                         MainNavHost(navController = navController, viewModel = viewModel, context = context, database = fbDB)

                    }
                }
            }
        }
    }
}

@Composable
fun NavigationIcon(icon: ImageVector, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(imageVector = icon, contentDescription = null)
    }
}

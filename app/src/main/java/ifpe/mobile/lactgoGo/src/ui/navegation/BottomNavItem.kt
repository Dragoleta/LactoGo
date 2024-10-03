package ifpe.mobile.lactgoGo.src.ui.navegation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title: String, var icon: ImageVector, var route: String)
{
    data object EditProfilePage : BottomNavItem("Meu Perfil", Icons.Default.Person, "edit-profile")
    data object HomePage : BottomNavItem("Início", Icons.Default.Home, "home")
    data object LogoutPage : BottomNavItem("Sair", Icons.AutoMirrored.Filled.ExitToApp, "logout")
}
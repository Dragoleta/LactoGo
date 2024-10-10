package ifpe.mobile.lactgoGo.src.ui.navegation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ifpe.mobile.lactgoGo.src.MVM.MainViewModel
import ifpe.mobile.lactgoGo.src.database.db.FirebaseDB
import ifpe.mobile.lactgoGo.src.ui.pages.EditProfilePage
import ifpe.mobile.lactgoGo.src.ui.pages.ExplorePageComp
import ifpe.mobile.lactgoGo.src.ui.pages.RegisterPlacePage
import ifpe.mobile.lactgoGo.src.ui.pages.RestaurantInfoPage


@Composable
fun MainNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    context: Context,
    database: FirebaseDB
) {
    NavHost(navController, startDestination = BottomNavItem.HomePage.route) {
        composable(route = BottomNavItem.HomePage.route) {
            ExplorePageComp(viewModel = viewModel, context = context, navController = navController)
        }
        composable("register") {
            RegisterPlacePage(modifier = modifier, database = database, navController = navController)
        }
        composable("rest-info") {
            RestaurantInfoPage(viewModel = viewModel, modifier = modifier)
        }
        composable(BottomNavItem.EditProfilePage.route) {
            EditProfilePage(viewModel = viewModel, database = database, navController = navController)
        }
    }
}
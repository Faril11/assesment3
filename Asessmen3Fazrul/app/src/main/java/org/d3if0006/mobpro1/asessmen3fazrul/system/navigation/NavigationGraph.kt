package org.d3if0006.mobpro1.asessmen3fazrul.system.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.d3if0006.mobpro1.asessmen3fazrul.system.database.mainViewModel
import org.d3if0006.mobpro1.asessmen3fazrul.system.database.model.User
import org.d3if0006.mobpro1.asessmen3fazrul.ui.screen.PublicGrid
import org.d3if0006.mobpro1.asessmen3fazrul.ui.screen.ScreenContent

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier, viewModel: mainViewModel, user: User) {
    NavHost(
        navController = navController,
        startDestination = Screen.Base.route
    ) {
        /*----------------[Main Route]----------------*/
        composable(route = Screen.Base.route) {
            PublicGrid("Home", viewModel = viewModel, modifier = modifier, user = user)
        }
    }
}
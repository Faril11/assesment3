package org.d3if0006.mobpro1.asessmen3fazrul.system.navigation

sealed class Screen(val route: String) {
    data object Base : Screen("home")
}
package eu.holker.builders.conf

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Distance : Routes("distance")
}
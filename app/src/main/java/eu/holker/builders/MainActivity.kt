package eu.holker.builders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.holker.builders.conf.Routes
import eu.holker.builders.screens.distance.Distance
import eu.holker.builders.screens.home.Home
import eu.holker.builders.ui.theme.BuildersTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BuildersTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ScreenMain()
                }
            }
        }
    }
}

@Composable
fun ScreenMain() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Home.route) {
        composable(Routes.Home.route) {
            Home(navController = navController)
        }

        composable(Routes.Distance.route) {
            Distance()
        }
    }
}
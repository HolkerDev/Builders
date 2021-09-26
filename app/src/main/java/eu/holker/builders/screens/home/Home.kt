package eu.holker.builders.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import eu.holker.builders.R

@Composable
fun Home(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Logo()
            MenuItem(title = "Расчитать расстояния", navController)
        }
    }
}

@Composable
fun MenuItem(title: String, navigator: NavHostController) {
    Button(onClick = { navigator.navigate("distance") }) {
        Text(text = title)
    }
}


@Composable
fun Logo() {
    Image(painter = painterResource(id = R.drawable.logo_transparent), contentDescription = "Logo")
}
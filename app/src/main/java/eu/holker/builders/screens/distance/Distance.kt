package eu.holker.builders.screens.distance

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Distance() {
    val viewModel = DistanceVM()
    val state = viewModel.distanceState.observeAsState()
    Box(
        modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.Center
    ){
        Column {
            Button(onClick = { /*TODO*/ }) {
                Text("Расчитать!")
            }
        }
    }
}

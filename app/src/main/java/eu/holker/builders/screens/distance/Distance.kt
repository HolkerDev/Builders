package eu.holker.builders.screens.distance

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun Distance() {
    val viewModel = DistanceVM()
    val state = viewModel.distanceState.observeAsState()
    Box(
        modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        var distanceString by remember { mutableStateOf("") }
        var stepString by remember { mutableStateOf("") }
        Column {
            Row {
                TextField(
                    value = distanceString,
                    onValueChange = { value -> distanceString = value },
                    label = { Text("Расстояние в см.") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            Row {
                TextField(
                    value = stepString,
                    onValueChange = { value -> stepString = value },
                    label = { Text("Шаг в см.") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            Button(onClick = {
                viewModel.computeDistancesList(distanceString, stepString)
            }) {
                Text("Расчитать!")
            }
            when (state.value) {
                is DistanceState.OK -> {
                    Text("OK")
                }
                DistanceState.WrongFormat -> {
                    Text("Wrong format")
                }
                DistanceState.NegativeNumbers -> {
                    Text("Negative Numbers")
                }
                DistanceState.DistanceLessThanStep -> {
                    Text(text = "Lower greater issue")
                }
            }
        }
    }
}

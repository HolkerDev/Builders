package eu.holker.builders.screens.distance

import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun Distance() {
    val viewModel = DistanceVM()
    val state = viewModel.distanceState.observeAsState()
    Box(
        modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.TopCenter
    ) {
        var distanceString by remember { mutableStateOf("") }
        var stepString by remember { mutableStateOf("") }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.padding(20.dp))

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

            Spacer(modifier = Modifier.padding(20.dp))

            Box {
                Button(onClick = {
                    viewModel.computeDistancesList(distanceString, stepString)
                }) {
                    Text("Расчитать!")
                }
            }

            Spacer(modifier = Modifier.padding(20.dp))

            DistancesInfoField(state = state)
        }
    }
}

@Composable
fun DistancesInfoField(state: State<DistanceState?>) {
    when (state.value) {
        is DistanceState.OK -> {
            Text("OK")
        }
        DistanceState.WrongFormat -> {
            Text("Неправильный формат входных данных")
        }
        DistanceState.NegativeNumbers -> {
            Text("Отрицательные значения не поддерживаются")
        }
        DistanceState.DistanceLessThanStep -> {
            Text(text = "Расстояние должно быть больше шага")
        }
    }
}


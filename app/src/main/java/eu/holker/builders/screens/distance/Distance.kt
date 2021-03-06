package eu.holker.builders.screens.distance

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.WindowManager
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Preview
@Composable
fun Distance() {
    val viewModel = DistanceVM()
    val state = viewModel.distanceState.observeAsState()
    KeepScreenOn()
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

            Spacer(modifier = Modifier.padding(10.dp))

            DistancesInfoField(state = state)
        }
    }
}

@Composable
fun DistancesInfoField(state: State<DistanceState?>) {
    when (state.value) {
        is DistanceState.OK -> {
            val response = (state.value as DistanceState.OK)
            val residue = response.residue
            Box(contentAlignment = Alignment.BottomCenter) {
                DistanceResidue(residue)
            }

            Spacer(modifier = Modifier.padding(5.dp))

            LazyColumn {
                items(response.distancesList.size) { index ->
                    val distancePair = response.distancesList[index]
                    DistanceItem(start = distancePair.first, end = distancePair.second)
                }
            }
        }
        DistanceState.WrongFormat -> {
            Text("Неправильный формат входных данных")
        }
        DistanceState.NegativeNumbers -> {
            Text("Отрицательные значения не поддерживаются")
        }
        DistanceState.DistanceLessThanStep -> {
            Text("Расстояние должно быть больше шага")
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun DistanceItem(start: Double, end: Double) {
    Row(
        modifier = Modifier
            .fillMaxSize(), horizontalArrangement = Arrangement.Center
    ) {
        Text("$start -> $end", fontSize = TextUnit(24f, TextUnitType.Sp))
    }
    Spacer(modifier = Modifier.padding(2.dp))
}

@Composable
fun DistanceResidue(residue: Double) {
    Row {
        Text(text = "Остаток: $residue см.")
    }
}

@Composable
fun KeepScreenOn() {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val window = context.findActivity()?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }
}

private fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}
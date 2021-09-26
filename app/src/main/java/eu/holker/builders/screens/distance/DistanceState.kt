package eu.holker.builders.screens.distance

sealed class DistanceState {
    data class OK(val distancesList: List<Pair<Double, Double>>) : DistanceState()
    object NegativeNumbers : DistanceState()
    object WrongFormat : DistanceState()
}
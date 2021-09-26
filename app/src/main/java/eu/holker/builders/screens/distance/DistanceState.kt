package eu.holker.builders.screens.distance

sealed class DistanceState {
    data class OK(val distancesList: List<Pair<Double, Double>>, val residue: Double) :
        DistanceState()

    object NegativeNumbers : DistanceState()
    object WrongFormat : DistanceState()
}
package eu.holker.builders.screens.distance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.holker.builders.screens.distance.DistanceState.*
import kotlin.math.round

class DistanceVM : ViewModel() {

    private var state = MutableLiveData<DistanceState>()

    val distanceState: LiveData<DistanceState>
        get() = state

    fun computeDistancesList(
        distanceString: String,
        stepString: String
    ) {
        try {
            val distance: Double = distanceString.toDouble()
            val step: Double = stepString.toDouble()
            if (step < 0 || distance < 0) {
                state.value = NegativeNumbers
                return
            } else if ((step > distance).or(step == 0.0)) {
                state.value = DistanceLessThanStep
                return
            }
            var currentSum = 0.0
            val listOfDistances = mutableListOf<Pair<Double, Double>>()
            while (distance >= currentSum + step) {
                val endOfDistance = currentSum + step
                listOfDistances.add(currentSum.round() to endOfDistance.round())
                currentSum += step
                currentSum.round()
            }
            state.value = OK(listOfDistances, distance - currentSum)
        } catch (e: NumberFormatException) {
            state.value = WrongFormat
        }
    }

    private fun Double.round(): Double {
        return round(this * 100) / 100
    }
}
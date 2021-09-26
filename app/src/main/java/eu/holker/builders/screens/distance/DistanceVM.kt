package eu.holker.builders.screens.distance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.holker.builders.screens.distance.DistanceState.*

class DistanceVM : ViewModel() {

    private var _distanceState = MutableLiveData<DistanceState>()

    val distanceState: LiveData<DistanceState>
        get() = _distanceState

    fun computeDistancesList(
        distanceString: String,
        stepString: String
    ) {
        try {
            val distance: Double = distanceString.toDouble()
            val step: Double = stepString.toDouble()
            if (step < 0 || distance < 0) {
                _distanceState.value = NegativeNumbers
                return
            }
            var currentSum = 0.0
            val listOfDistances = mutableListOf<Pair<Double, Double>>()
            while (distance >= currentSum + step) {
                listOfDistances.add(currentSum to currentSum + step)
                currentSum += step
            }
            _distanceState.value = OK(listOfDistances)
        } catch (e: NumberFormatException) {
            _distanceState.value = WrongFormat
        }
    }
}
package eu.holker.builders

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import eu.holker.builders.screens.distance.DistanceState.*
import eu.holker.builders.screens.distance.DistanceVM
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class DistanceTests {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val viewModel = DistanceVM()

    @Test
    fun `should return OK with empty list of distances if step is bigger than distance`() {
        val distance = "10"
        val step = "25"
        viewModel.computeDistancesList(distanceString = distance, stepString = step)
        assertTrue(viewModel.distanceState.value is OK)
        assert(viewModel.stateDistancesList().isEmpty())
    }

    @Test
    fun `should return OK with one distance list`() {
        val distance = "15"
        val step = "10"
        viewModel.computeDistancesList(distance, step)

        assertTrue(viewModel.distanceState.value is OK)
        assertEquals(viewModel.stateDistancesList().size, 1)
        assert(viewModel.stateResidue() == 5.0)
    }

    @Test
    fun `should return OK with two distances list`() {
        val distance = "30"
        val step = "15"
        viewModel.computeDistancesList(distance, step)
        assertTrue(viewModel.distanceState.value is OK)
        assertEquals(viewModel.stateDistancesList().size, 2)
    }

    @Test
    fun `should return WrongFormat if distance is not a number`() {
        val distance = "wrong_format"
        val step = "15"
        viewModel.computeDistancesList(distance, step)
        assert(viewModel.distanceState.value is WrongFormat)
    }

    @Test
    fun `should return WrongFormat if step is not a number`() {
        val distance = "15"
        val step = "wrong_format"
        viewModel.computeDistancesList(distance, step)
        assert(viewModel.distanceState.value is WrongFormat)
    }

    @Test
    fun `should return NegativeNumbers if any arguments are negative`() {
        val distance = "-15"
        val step = "-10"
        viewModel.computeDistancesList(distance, step)
        assert(viewModel.distanceState.value is NegativeNumbers)
    }
}

fun DistanceVM.stateDistancesList(): List<Pair<Double, Double>> =
    (this.distanceState.value as OK).distancesList

fun DistanceVM.stateResidue(): Double =
    (this.distanceState.value as OK).residue
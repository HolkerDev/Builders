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
    fun `should return DistanceLessThanStep if step is bigger than distance`() {
        val distance = "10"
        val step = "25"
        viewModel.computeDistancesList(distanceString = distance, stepString = step)
        assertTrue(viewModel.distanceState.value is DistanceLessThanStep)
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
        assert(viewModel.stateResidue() == 0.0)
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

    @Test
    fun `should return correct list of distances with 0 residue`() {
        val distance = "15"
        val step = "5"
        viewModel.computeDistancesList(distance, step)
        val list = viewModel.stateDistancesList()
        val residue = viewModel.stateResidue()
        assertEquals(list[0], 0.0 to 5.0)
        assertEquals(list[1], 5.0 to 10.0)
        assertEquals(residue, 0.0, 0.0)
    }

    @Test
    fun `should return correct list of distances with non 0 residue`() {
        val distance = "24"
        val step = "5"
        viewModel.computeDistancesList(distance, step)
        val list = viewModel.stateDistancesList()
        val residue = viewModel.stateResidue()
        assertEquals(list[0], 0.0 to 5.0)
        assertEquals(list[1], 5.0 to 10.0)
        assertEquals(list[2], 10.0 to 15.0)
        assertEquals(list[3], 15.0 to 20.0)
        assertEquals(residue, 4.0, 0.0)
    }

    @Test
    fun `should return Distance Less Than Step when step equals to 0`() {
        val distance = "0"
        val step = "0"
        viewModel.computeDistancesList(distance, step)
        assertTrue(viewModel.distanceState.value is DistanceLessThanStep)
    }

    @Test
    fun `should return correct list of distances for 1 sm step`() {
        val distance = "12"
        val step = "1"
        viewModel.computeDistancesList(distance, step)
        val list = viewModel.stateDistancesList()
        assertEquals(12, list.size)
        assertEquals(list[0], 0.0 to 1.0)
    }
}

fun DistanceVM.stateDistancesList(): List<Pair<Double, Double>> =
    (this.distanceState.value as OK).distancesList

fun DistanceVM.stateResidue(): Double =
    (this.distanceState.value as OK).residue
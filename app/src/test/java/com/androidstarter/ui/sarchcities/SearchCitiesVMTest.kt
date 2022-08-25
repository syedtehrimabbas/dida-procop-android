package com.androidstarter.ui.sarchcities

import com.androidstarter.common.CoroutineRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchCitiesVMTest {
    @Rule
    @JvmField
    var coroutineRule = CoroutineRule()

    lateinit var sut: SearchCitiesVM

    @Test
    fun `test search cities api success`() = runTest {
        sut = SearchCitiesVM(mockk())

        sut.searchCities("Lahore")

        val expected = true
        val actual = sut.geoNames.getOrAwaitValue().isNotEmpty()

        Assert.assertEquals(expected, actual)
    }

    @Before
    fun tearDown() {

    }
}
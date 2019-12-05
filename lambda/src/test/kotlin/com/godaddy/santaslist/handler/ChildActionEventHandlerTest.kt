package com.godaddy.santaslist.handler

import com.godaddy.santaslist.db.ChildService
import com.godaddy.santaslist.model.Child
import com.godaddy.santaslist.model.ChildAction
import com.godaddy.santaslist.model.ChildActionEvent
import com.godaddy.santaslist.util.PointsCalculator
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import kotlin.test.assertEquals

@DisplayName("ChildActionEventHandler should")
internal class ChildActionEventHandlerTest {

    companion object {
        private val CHILD = Child(1, "Stevie", 1, "987 Broadway", 25)
    }

    @Mock lateinit var childService: ChildService

    lateinit var pointsCalculator: PointsCalculator
    lateinit var unit: ChildActionEventHandler

    @BeforeEach
    internal fun setUp() {
        initMocks(this)

        pointsCalculator = spy(PointsCalculator())
        unit = ChildActionEventHandler(childService, pointsCalculator)
    }

    @DisplayName("take points away from thieves")
    @ParameterizedTest(name = "age: {0}")
    @ValueSource(ints = [2, 4, 7, 11, 52])
    internal fun stealing(age: Int) {
        whenever(childService.getChild(1))
            .thenReturn(CHILD.copy(age = age))

        unit.handle(ChildActionEvent(1, ChildAction.StealToy))

        verify(pointsCalculator).pointsDiff(ChildAction.StealToy, age)
        argumentCaptor<Child>().apply {
            verify(childService).putChild(capture())
            assertAll(
                { assertEquals(1, firstValue.id, "wrong id") },
                { assert(firstValue.goodness < 25) { "expected fewer points" } }
            )
        }
    }
}
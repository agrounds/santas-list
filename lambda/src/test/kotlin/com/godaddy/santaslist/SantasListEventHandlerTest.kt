package com.godaddy.santaslist

import com.godaddy.santaslist.handler.EventHandler
import com.godaddy.santaslist.model.AddChildEvent
import com.godaddy.santaslist.model.ChildAction
import com.godaddy.santaslist.model.ChildActionEvent
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

@DisplayName("SantasListEventHandler should")
internal class SantasListEventHandlerTest {

    @Mock lateinit var addChildEventHandler: EventHandler<AddChildEvent>
    @Mock lateinit var childActionEventHandler: EventHandler<ChildActionEvent>

    lateinit var unit: SantasListEventHandler

    @BeforeEach
    internal fun setUp() {
        initMocks(this)
        unit = SantasListEventHandler(addChildEventHandler, childActionEventHandler)
    }

    @DisplayName("send AddChildEvents to correct handler")
    @Test
    internal fun addChildEvent() {
        unit.handleEvent(AddChildEvent(1, "Susie", 8, "123 Park Place"))

        verify(addChildEventHandler).handle(any())
        verifyZeroInteractions(childActionEventHandler)
    }

    @DisplayName("send ChildActionEvents to correct handler")
    @ParameterizedTest(name = "child action type: {0}")
    @EnumSource(ChildAction::class)
    internal fun childActionEvents(action: ChildAction) {

        unit.handleEvent(ChildActionEvent(1, action))

        verify(childActionEventHandler).handle(any())
        verifyZeroInteractions(addChildEventHandler)
    }

    @DisplayName("throw errors for unknown event types")
    @Test
    internal fun unknownEvent() {
        assertThrows<RuntimeException> {
            unit.handleEvent("bad event!")
        }
    }
}
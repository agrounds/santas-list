package com.godaddy.santaslist

import com.godaddy.santaslist.handler.AddChildEventHandler
import com.godaddy.santaslist.handler.ChildActionEventHandler
import com.godaddy.santaslist.model.AddChildEvent
import com.godaddy.santaslist.model.ChildActionEvent

class SantasListEventHandler(
    private val addChildEventHandler: AddChildEventHandler,
    private val childActionEventHandler: ChildActionEventHandler
) {
    fun handleEvent(event: Any) {
        when (event) {
            is AddChildEvent -> addChildEventHandler.handle(event)
            is ChildActionEvent -> childActionEventHandler.handle(event)
            else -> throw RuntimeException("Unexpected event type! $event")
        }
    }
}
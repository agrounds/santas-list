package com.godaddy.santaslist

import com.godaddy.santaslist.handler.EventHandler
import com.godaddy.santaslist.model.AddChildEvent
import com.godaddy.santaslist.model.ChildActionEvent

class SantasListEventHandler(
    private val addChildEventHandler: EventHandler<AddChildEvent>,
    private val childActionEventHandler: EventHandler<ChildActionEvent>
) {
    fun handleEvent(event: Any) {
        when (event) {
            is AddChildEvent -> addChildEventHandler.handle(event)
            is ChildActionEvent -> childActionEventHandler.handle(event)
            else -> throw RuntimeException("Unexpected event type! $event")
        }
    }
}
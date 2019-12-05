package com.godaddy.santaslist.handler

import com.godaddy.santaslist.db.ChildService
import com.godaddy.santaslist.model.ChildActionEvent
import com.godaddy.santaslist.util.PointsCalculator

class ChildActionEventHandler(
    private val childService: ChildService,
    private val pointsCalculator: PointsCalculator
) : EventHandler<ChildActionEvent> {

    override fun handle(event: ChildActionEvent) {
        val child = childService.getChild(event.childId)
        if (child != null) {
            val diff = pointsCalculator.pointsDiff(event.action, child.age)
            childService.putChild(child.copy(goodness = child.goodness + diff))
        }
    }
}
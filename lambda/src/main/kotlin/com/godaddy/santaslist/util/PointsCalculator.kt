package com.godaddy.santaslist.util

import com.godaddy.santaslist.model.ChildAction

open class PointsCalculator {
    open fun pointsDiff(action: ChildAction, age: Int): Int {
        return when (action) {
            ChildAction.CryForNoReason -> when {
                age < 5 -> -1
                age < 8 -> -4
                else -> -10
            }
            ChildAction.ReadBook -> when {
                age < 5 -> 30
                age < 8 -> 12
                else -> 8
            }
            ChildAction.StealToy -> when {
                age < 5 -> -2
                age < 8 -> -10
                else -> -20
            }
            ChildAction.TellParentILoveYou -> 1000
        }
    }
}
package com.godaddy.santaslist.db

import com.godaddy.santaslist.model.Child

interface ChildDao {
    fun putChild(child: Child)
    fun getChild(id: Int): Child?
}
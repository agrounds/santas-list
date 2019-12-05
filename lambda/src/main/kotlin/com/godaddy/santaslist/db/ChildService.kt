package com.godaddy.santaslist.db

import com.godaddy.santaslist.model.Child

interface ChildService {
    fun putChild(child: Child)
    fun getChild(id: Int): Child?
}

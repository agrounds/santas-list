package com.godaddy.santaslist.db

import com.godaddy.santaslist.model.Child

class ChildServiceImpl(
    private val childDao: ChildDao,
    private val childCache: Cache<Child>
) : ChildService {
    override fun putChild(child: Child) {
        TODO("not implemented")
    }

    override fun getChild(id: Int): Child? {
        TODO("not implemented")
    }
}
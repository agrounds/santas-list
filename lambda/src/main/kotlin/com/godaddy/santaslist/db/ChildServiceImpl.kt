package com.godaddy.santaslist.db

import com.godaddy.santaslist.model.Child

class ChildServiceImpl(
    private val childDao: ChildDao,
    private val childCache: Cache<Child>
) : ChildService {
    override fun putChild(child: Child) {
        if (childCache.get(child.id) != child) {
            childCache.put(child.id, child)
            childDao.putChild(child)
        }
    }

    override fun getChild(id: Int): Child? {
        return childCache.get(id) ?: childDao.getChild(id)
    }
}
package com.godaddy.santaslist.db

interface Cache<T> {
    fun get(id: Int): T?
    fun put(id: Int, data: T)
}
package com.godaddy.santaslist.handler

interface EventHandler<T> {
    fun handle(event: T)
}
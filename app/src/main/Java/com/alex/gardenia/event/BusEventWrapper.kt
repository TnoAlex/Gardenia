package com.alex.gardenia.event

data class BusEventWrapper(val target: BusEventTarget, private val data: Any) {

    fun data(location: BusEventTarget): Any? {
        return if (location == target)
            data
        else null
    }
}
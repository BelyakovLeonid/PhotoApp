package com.example.photoapp.local

class Event<T>(private val value: T) {
    private var wasValueHandled = false

    fun getValueIfNotHandled(): T? {
        return if (wasValueHandled) {
            null
        } else {
            wasValueHandled = true
            value
        }
    }

    fun getValue(): T {
        return value
    }
}
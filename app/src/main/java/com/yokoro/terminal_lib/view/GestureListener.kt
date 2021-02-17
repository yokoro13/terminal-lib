package com.yokoro.terminal_lib.view

import java.util.*

interface GestureListener: EventListener{
    fun onDown()
    fun onMove()
}
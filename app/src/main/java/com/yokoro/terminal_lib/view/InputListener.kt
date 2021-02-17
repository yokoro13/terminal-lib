package com.yokoro.terminal_lib.view

import java.util.*

interface InputListener: EventListener {
    fun onKey(text: Char)
}
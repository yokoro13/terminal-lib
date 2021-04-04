package com.yokoro.terminal_lib.entity

/**
 *
 */
data class Cursor(
    var x: Int,
    var y: Int
) {
    var isDisplaying: Boolean = false
}
package com.yokoro.terminal_lib.entity

import entity.Cursor
import entity.ScreenSize
import entity.TerminalArray

/**
 * ターミナルの画面情報を扱う
 * @param screenColumnSize : 画面の横幅
 * @param screenRowSize : 画面の縦幅
 */
data class Terminal(
    var screenSize: ScreenSize
) {
    var cursor: Cursor = Cursor(0, 0)
    var terminalBuffer: ArrayList<TerminalArray> = ArrayList<TerminalArray>()
    var topRow: Int = 0
    set(value) {
        field = if (value < 0) {
            0
        } else {
            value
        }
    }
}


package com.yokoro.terminal_lib.entity

/**
 * ターミナルの画面情報を扱う
 * @param screenColumnSize : 画面の横幅
 * @param screenRowSize : 画面の縦幅
 */
class Terminal(
    var screenSize: ScreenSize
) {
    var terminalBuffer: ArrayList<TerminalRow> = ArrayList<TerminalRow>()
    var topRow: Int = 0
    set(value) {
        if (value < 0) {
            field = 0
        }
    }
}

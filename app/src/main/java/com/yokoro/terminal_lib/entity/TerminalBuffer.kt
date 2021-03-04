package com.yokoro.terminal_lib.entity

/**
 * ターミナルの画面情報を扱う
 * @param screenColumnSize : 画面の横幅
 * @param screenRowSize : 画面の縦幅
 */
class TerminalBuffer(
    private val screenSize: ScreenSize
) {
    var textBuffer: ArrayList<TerminalRow> = ArrayList<TerminalRow>()
}

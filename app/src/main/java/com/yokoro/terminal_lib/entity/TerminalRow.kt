package com.yokoro.terminal_lib.entity

/**
 * １文字を表すクラス
 * 文字の情報と色の情報をもつ
 * 色は16進数(#AARRGGBB)
 */

data class TerminalRow(
    var text: CharArray,
    var color: IntArray,
    var lineWrap: Boolean
)

package entity

/**
 * １文字を表すクラス
 * 文字の情報と色の情報をもつ
 * 色は16進数(#AARRGGBB)
 */

data class TerminalArray (
    var terminalRow: ArrayList<TerminalChar>,
    var lineWrapPos: Int
)

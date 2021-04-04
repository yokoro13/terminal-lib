package com.yokoro.terminal_lib.usecase.terminalbuffer

import com.yokoro.terminal_lib.entity.TerminalRow

/**
 * TerminalBuffer に関する UseCase を定義
 */
interface ITerminalBufferUseCase {

    suspend fun addNewRow(warp: Boolean)

    suspend fun getTerminalBuffer(): ArrayList<TerminalRow>

    suspend fun setText(x: Int, y: Int, text: Char)

    suspend fun setColor(x: Int, y: Int, color: Int)
}
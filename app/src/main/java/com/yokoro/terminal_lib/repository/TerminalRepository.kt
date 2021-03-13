package com.yokoro.terminal_lib.repository

import arrow.core.Either
import arrow.core.None
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.entity.ScreenSize
import com.yokoro.terminal_lib.entity.TerminalBuffer
import com.yokoro.terminal_lib.entity.TerminalRow

/**
 * データの保存，取得をおこなう
 *
 */
class TerminalRepository: ITerminalRepository {
    override fun createTerminalBuffer(): Either<Failure, None> {
        TODO("Not yet implemented")
    }

    override fun getScreenSize(): Either<Failure, ScreenSize> {
        TODO("Not yet implemented")
    }

    override fun addRow(lineWarp: Boolean): Either<Failure, TerminalBuffer> {
        TODO("Not yet implemented")
    }

    override fun setText(x: Int, y: Int, text: Char): Either<Failure, TerminalBuffer> {
        TODO("Not yet implemented")
    }

    override fun setColor(x: Int, y: Int, color: Int): Either<Failure, TerminalBuffer> {
        TODO("Not yet implemented")
    }

    override fun getTextBuffer(): Either<Failure, ArrayList<TerminalRow>> {
        TODO("Not yet implemented")
    }

    override fun resize(
        newScreenColumnSize: Int,
        newScreenRowSize: Int
    ): Either<Failure, TerminalBuffer> {
        TODO("Not yet implemented")
    }

    override fun getTopRow(): Either<Failure, Int> {
        TODO("Not yet implemented")
    }

    override fun setTopRow(n: Int): Either<Failure, None> {
        TODO("Not yet implemented")
    }
}
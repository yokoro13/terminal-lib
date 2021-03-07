package com.yokoro.terminal_lib.repository

import arrow.core.Either
import com.yokoro.terminal_lib.core.Failure
import com.yokoro.terminal_lib.core.UseCase
import com.yokoro.terminal_lib.entity.ScreenSize
import com.yokoro.terminal_lib.entity.TerminalBuffer

class TerminalRepository: ITerminalRepository {
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

    override fun getRowCharArrayAt(y: Int): Either<Failure, CharArray> {
        TODO("Not yet implemented")
    }

    override fun resize(
        newScreenColumnSize: Int,
        newScreenRowSize: Int
    ): Either<Failure, TerminalBuffer> {
        TODO("Not yet implemented")
    }

    override fun getCurrentRow(): Either<Failure, Int> {
        TODO("Not yet implemented")
    }

    override fun setCurrentRow(n: Int): Either<Failure, UseCase.None> {
        TODO("Not yet implemented")
    }
}
package repository

import core.Either
import core.Failure
import core.getOrElse
import entity.Cursor
import entity.ScreenSize
import kotlin.test.Test
import kotlin.test.assertEquals

class TerminalRepositoryTest {

    private fun <T> getOrError(a: Either<Failure, T>): T =
        a.getOrElse { throw IllegalArgumentException("") }

    @Test
    fun testCreateTerminal(){
        val terminalRepository = TerminalRepository()
        val ss = ScreenSize(48, 20)
        terminalRepository.createTerminal(ss)
        assertEquals(1, getOrError(terminalRepository.getTerminalBuffer()).size)
    }
}
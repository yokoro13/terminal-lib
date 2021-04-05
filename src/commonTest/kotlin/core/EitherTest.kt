package core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class EitherTest {
    @Test
    fun testEitherRight() {
        val right = Either.Right(3)

        val result = right.getOrElse { throw IllegalArgumentException("fail")}

        assertEquals(3, result)
    }

    @Test
    fun testEitherLeft() {
        val left = Either.Left(2)

        val result = left.getOrElse { assertTrue(true) }
    }
}
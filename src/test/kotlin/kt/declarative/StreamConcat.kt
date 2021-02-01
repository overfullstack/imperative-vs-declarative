/* gakshintala created on 4/14/20 */
package kt.declarative

import kt.common.DELIMITER
import kt.common.EXPECTED_RESULT
import kt.common.TEAM
import kt.common.extractLastName
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.stream.Collectors
import java.util.stream.Stream

class StreamConcat {
    @Test
    fun testLastNameConcat() {
        val expected: String = concatLastNames(TEAM)
        val expectedParallel: String = concatLastNamesInParallel(TEAM)
        Assertions.assertEquals(expected, expectedParallel) // Parallel stream maintains the order
        Assertions.assertEquals(EXPECTED_RESULT, expected)
        println(expected)
    }

    @Test
    fun testLastNameConcatForNullTeam() {
        val expected = concatLastNames(null)
        val expectedParallel = concatLastNamesInParallel(null)
        Assertions.assertEquals(expected, expectedParallel)
        Assertions.assertEquals("", expected)
    }

    companion object {
        private fun concatLastNames1(team: List<String>): String {
            return team.asSequence()
                    .map(::extractLastName)
                    .joinToString(DELIMITER)
        }

        /**
         * ∙ Patching up -> Extending
         * ∙ Control Statements -> Expressions
         * ∙ Code that talks
         * ∙ Puzzle pieces fit together.
         */
        fun concatLastNames(team: List<String?>?): String = (team?.asSequence() ?: emptySequence()) // HTD-1: Looping
                .filterNotNull() // WTD-11: Deal with nulls.
                .map { it.trim() } // WTD-12: Deal with only-white-space strings.
                .filter { it.isNotEmpty() } // WTD-13: Deal with empty strings.
                .map(::extractLastName) // WTD-2: Extract Last Name.
            // Till here it's only an expression. The Terminal operator below executes it.
                .joinToString(DELIMITER) // HTD-2: Aggregating results.

        fun concatLastNamesInParallel(team: List<String?>?): String = (team?.parallelStream() ?: Stream.empty()) // HTD-1
                .filter { it != null } // WTD-11: Deal with nulls.
                .map { it!!.trim() } // WTD-12: Deal with only-white-space strings.
                .filter { it.isNotEmpty() } // WTD-13: Deal with empty strings.
                .map(::extractLastName) // WTD-2: Extract Last Name.
            // Till here it's only an expression. The Terminal operator below executes it.
                .collect(Collectors.joining(DELIMITER)) // HTD-2: Aggregating results.
    }
}

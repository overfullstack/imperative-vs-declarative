package kt.imperative

import kt.common.DELIMITER
import kt.common.EXPECTED_RESULT
import kt.common.TEAM
import kt.common.extractLastName
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ImperativeLastName {

    @Test
    fun `1 - LastName Concat`() {
        val actual = concatLastName1(TEAM)
        assertEquals(EXPECTED_RESULT, actual)
    }

    @Test
    fun `2 - LastName Concat`() {
        val actual = concatLastName2(TEAM)
        assertEquals(EXPECTED_RESULT, actual)
    }

    @Test
    fun `3 - LastName Concat`() {
        val actual = concatLastNamesFinal(TEAM)
        assertEquals(EXPECTED_RESULT, actual)
    }

    /**
     * Java Dev's Kotlin.
     */
    companion object {
        private fun concatLastName1(team: List<String?>): String {
            var output = ""
            for (teamMemberName in team) {
                val lastName = extractLastName(teamMemberName)
                output += (lastName + DELIMITER)
            }
            return output
        }

        private fun concatLastName2(team: List<String?>?): String { // Also want to support null teams
            if (team == null) {
                return ""
            }
            var output = ""
            for (teamMemberName in team) {
                if (teamMemberName != null) {
                    val teamMemberNameTrimmed = teamMemberName.trim { it <= ' ' }
                    if (teamMemberNameTrimmed.isNotEmpty()) {
                        val lastName = extractLastName(teamMemberName)
                        output += (lastName + DELIMITER)
                    }
                }
            }
            return output
        }

        fun concatLastNamesFinal(team: List<String?>?): String {
            if (team == null) {
                return ""
            }
            var output = ""
            var isFirstFlag = true
            for (teamMemberName in team) { // HTD-1: Looping through the list
                if (teamMemberName != null) { // WTD-11: Deal with nulls
                    val teamMemberNameTrimmed = teamMemberName.trim { it <= ' ' } // WTD-12: Deal with only white space names
                    if (teamMemberNameTrimmed.isNotEmpty()) { // WTD-13: Deal with empty names
                        if (!isFirstFlag) { // Catch: Should not prepend delimiter for first entry.
                            output += DELIMITER
                        }
                        val lastName = extractLastName(teamMemberName) // WTD-2: Extracting last name
                        output += lastName // HTD-2: Aggregating the results with the delimiter.
                        isFirstFlag = false
                    }
                }
            }
            return output
        }
    }
}

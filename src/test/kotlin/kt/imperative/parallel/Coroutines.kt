/* gakshintala created on 4/19/20 */
package kt.imperative.parallel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kt.common.EXPECTED_RESULT
import kt.common.TEAM
import kt.imperative.ImperativeLastName.Companion.concatLastNames
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Coroutines {

    @Test
    fun parallelWithCoroutines() = runBlocking {
        val actualResult = concat(TEAM)
        println(actualResult)
        Assertions.assertEquals(EXPECTED_RESULT, actualResult)
    }

    companion object {
        private const val MIN_TEAM_SIZE = 2

        suspend fun concat(team: List<String?>): String = withContext(Dispatchers.Default) {
            if (team.size > MIN_TEAM_SIZE) {
                val mid = team.size / 2
                val leftTask = async { concat(team.subList(0, mid)) }
                val rightTask = async { concat(team.subList(mid, team.size)) }
                concatResults(leftTask.await(), rightTask.await())
            } else {
                concatLastNames(team)
            }
        }
    }
}

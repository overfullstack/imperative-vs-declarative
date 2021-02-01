/* gakshintala created on 4/19/20 */
package kt.imperative.parallel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kt.common.EXPECTED_RESULT
import kt.common.TEAM
import kt.imperative.ImperativeConcat.Companion.concatLastNames
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CoroutinesConcat {

    @Test
    fun parallelWithCoroutines() = runBlocking {
        val actualResult = concat(TEAM)
        println(actualResult)
        Assertions.assertEquals(EXPECTED_RESULT, actualResult)
    }

    companion object {
        private const val MIN_TEAM_SIZE = 2

        suspend fun concat(team: List<String?>?): String = team?.let {
            withContext(Dispatchers.Default) {
                if (it.size > MIN_TEAM_SIZE) {
                    val mid = it.size / 2
                    val leftTask = async { concat(it.subList(0, mid)) }
                    val rightTask = async { concat(it.subList(mid, it.size)) }
                    concatResults(leftTask.await(), rightTask.await())
                } else {
                    concatLastNames(team)
                }
            }
        } ?: ""
    }
}

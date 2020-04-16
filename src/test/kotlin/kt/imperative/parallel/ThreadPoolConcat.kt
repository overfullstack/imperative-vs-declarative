/* gakshintala created on 4/14/20 */
package kt.imperative.parallel

import kt.common.TEAM
import imperative.ImperativeLastName
import kt.common.EXPECTED_RESULT
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class ThreadPoolConcat {
    @Test
    fun testLastNameFinderThreadPool() {
        val actualResult = parallelWithThreadPool(TEAM)
        println(actualResult)
        Assertions.assertEquals(EXPECTED_RESULT, actualResult)
    }

    companion object {
        fun parallelWithThreadPool(team: List<String?>): String {
            val executor = Executors.newFixedThreadPool(AVAILABLE_CORES - 1)
            val futureList = ArrayList<Future<String>>()
            var segmentLen = team.size / AVAILABLE_CORES
            if (segmentLen == 0) {
                segmentLen = team.size
            }

            // Split the list to be dealt by different futures.
            var offset = 0
            while (offset < team.size) {
                val from = offset
                val to = offset + segmentLen
                futureList.add(executor.submit<String> { ImperativeLastName.concatLastNames(team.subList(from, to)) })
                offset += segmentLen
            }

            // Aggregate results
            val results = ArrayList<String>()
            for (future in futureList) {
                try {
                    results.add(future.get())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } catch (e: ExecutionException) {
                    e.printStackTrace()
                }
            }

            // Deal with last left-out segment
            if (offset < team.size) {
                results.add(ImperativeLastName.concatLastNames(team.subList(team.size - segmentLen, team.size)))
            }
            executor.shutdown()
            try {
                executor.awaitTermination(10, TimeUnit.SECONDS)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            return concatResults(results)
        }
    }
}

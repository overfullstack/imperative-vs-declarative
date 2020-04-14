/* gakshintala created on 4/14/20 */
package kt.imperative.parallel

import common.Common
import common.Common.TEAM
import kt.imperative.ImperativeLastName.Companion.concatLastNamesFinal
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.RecursiveTask

internal class MyRecursiveTask(private val team: List<String>) : RecursiveTask<String>() {
    override fun compute(): String = if (team.size > MIN_TEAM_SIZE) {
        val mid = team.size / 2
        val myRecursiveTask1 = MyRecursiveTask(team.subList(0, mid))
        val myRecursiveTask2 = MyRecursiveTask(team.subList(mid, team.size))
        myRecursiveTask1.fork()
        myRecursiveTask2.fork()
        val results = ArrayList<String>()
        results.add(myRecursiveTask1.join())
        results.add(myRecursiveTask2.join())
        concatResults(results)
    } else {
        concatLastNamesFinal(team)
    }

    companion object {
        private const val serialVersionUID = -5978274303314688527L
        private const val MIN_TEAM_SIZE = 2 // In real-world, DO NOT have it below 10,000
    }
}

class ForkJoinConcat {

    @Test
    fun parallelWithForkJoinPool() {
        val forkJoinPool = ForkJoinPool(AVAILABLE_CORES)
        val actualResult = forkJoinPool.invoke(MyRecursiveTask(TEAM))
        println(actualResult)
        Assertions.assertEquals(Common.EXPECTED_RESULT, actualResult)
    }
}

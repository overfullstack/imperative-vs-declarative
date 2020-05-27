/* gakshintala created on 4/14/20 */
package kt.imperative.parallel

import kt.common.DELIMITER
import java.util.concurrent.RecursiveTask

val AVAILABLE_CORES = Runtime.getRuntime().availableProcessors()

fun concatResults(results: List<String>): String {
    var output = ""
    var isFirst = true
    for (result in results) {
        if (result.isNotEmpty()) {
            if (!isFirst) {
                output += DELIMITER
            }
            output += result
            isFirst = false
        }
    }
    return output
}

fun concatResultsFromForks(myRecursiveTask1: RecursiveTask<String>, myRecursiveTask2: RecursiveTask<String>): String {
    val result1 = myRecursiveTask1.join()
    val result2 = myRecursiveTask2.join()
    return concatResults(result1, result2)
}

fun concatResults(result1: String, result2: String): String =
        when {
            result1.isEmpty() -> result2
            result2.isEmpty() -> result1
            else -> result1 + DELIMITER + result2
        }

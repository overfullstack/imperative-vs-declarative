/* gakshintala created on 4/14/20 */
package kt.imperative.parallel

import kt.common.DELIMITER

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

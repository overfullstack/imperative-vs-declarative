package imperative.parallel;

import java.util.List;
import java.util.concurrent.RecursiveTask;

import static common.Common.DELIMITER;

final class Util {

    static final int AVAILABLE_CORES = Runtime.getRuntime().availableProcessors();

    static String concatResults(List<String> results) {
        var output = new StringBuilder();
        var isFirst = true;
        for (var result : results) {
            if (!result.isEmpty()) {
                if (!isFirst) {
                    output.append(DELIMITER);
                }
                output.append(result);
                isFirst = false;
            }
        }
        return output.toString();
    }

    static String concatResultsFromForks(RecursiveTask<String> recursiveTask1, RecursiveTask<String> recursiveTask2) {
        var result1 = recursiveTask1.join();
        var result2 = recursiveTask2.join();
        if (result1.isEmpty()) {
            return result2;
        } else if (result2.isEmpty()) {
            return result1;
        } else {
            return result1 + DELIMITER + result2;
        } 
    }
}

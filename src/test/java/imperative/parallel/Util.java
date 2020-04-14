package imperative.parallel;

import java.util.List;

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
}

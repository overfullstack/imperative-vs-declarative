package imperative.parallel;

import java.util.List;

final class Util {
    static String concatResults(List<String> results) {
        var output = new StringBuilder();
        var isFirst = true;
        for (var result : results) {
            if (!result.isEmpty()) {
                if (!isFirst) {
                    output.append(" 🤝 ");
                }
                output.append(result);
                isFirst = false;
            }
        }
        return output.toString();
    }
}

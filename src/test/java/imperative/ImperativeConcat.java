package imperative;

import org.junit.jupiter.api.Test;

import java.util.List;

import static common.Common.DELIMITER;
import static common.Common.EXPECTED_RESULT;
import static common.Common.TEAM;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImperativeConcat {

    private static String concatLastNames1(List<String> team) {
        var output = new StringBuilder();
        for (var teamMemberName : team) {
            var lastName = extractLastName(teamMemberName);
            output.append(lastName);
            output.append(DELIMITER);
        }
        return output.toString();
    }

    private static String concatLastNames2(List<String> team) {
        if (team == null) {
            return "";
        }
        var output = new StringBuilder();
        for (var teamMemberName : team) {
            if (teamMemberName != null) {
                teamMemberName = teamMemberName.trim();
                if (!teamMemberName.isEmpty()) {
                    var lastName = extractLastName(teamMemberName);
                    output.append(lastName);
                    output.append(DELIMITER);
                }
            }
        }
        return output.toString();
    }

    public static String concatLastNames(List<String> team) {
        if (team == null) {
            return "";
        }
        var output = new StringBuilder();
        var isFirstFlag = true;
        for (var teamMemberName : team) { // HTD-1: Looping through the list
            if (teamMemberName != null) { // WTD-11: Deal with nulls
                teamMemberName = teamMemberName.trim(); // WTD-12: Deal with only white space names
                if (!teamMemberName.isEmpty()) { // WTD-13: Deal with empty names
                    if (!isFirstFlag) { // Catch: Should not prepend delimiter for first entry.
                        output.append(DELIMITER);
                    }
                    var lastName = extractLastName(teamMemberName); // WTD-2: Extracting last name
                    output.append(lastName); // HTD-2: Aggregating the results with the delimiter.
                    isFirstFlag = false;
                }
            }
        }
        return output.toString();
    }

    private static String extractLastName(String fullName) {
        // Substring after last whitespace
        return fullName.substring(fullName.lastIndexOf(" ") + 1);
    }

    @Test
    void testLastNameConcatStage1() {
        final var actual = concatLastNames1(TEAM);
        assertEquals(EXPECTED_RESULT, actual);
        System.out.println(actual);
    }

    @Test
    void testLastNameConcatStage2() {
        final var actual = concatLastNames2(TEAM);
        assertEquals(EXPECTED_RESULT, actual);
        System.out.println(actual);
    }

    @Test
    void testLastNameConcat() {
        final var actual = concatLastNames(TEAM);
        assertEquals(EXPECTED_RESULT, actual);
        System.out.println(actual);
    }

}

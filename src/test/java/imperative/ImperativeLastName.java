package imperative;

import org.junit.jupiter.api.Test;

import java.util.List;

import static common.Common.DELIMITER;
import static common.Common.RESULT;
import static common.Common.TEAM;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImperativeLastName {

    @Test
    void testLastNameConcatStage1() {
        final var expected = concatLastNamesStage1(TEAM);
        assertEquals(RESULT, expected);
        System.out.println(expected);
    }

    public static String concatLastNamesStage1(List<String> teamNames) {
        StringBuilder output = new StringBuilder();
        for (String teamMemberName : teamNames) {
            String lastName = extractLastName(teamMemberName);
            output.append(lastName);
            output.append(DELIMITER);
        }
        return output.toString();
    }

    @Test
    void testLastNameConcatStage2() {
        final var expected = concatLastNamesStage2(TEAM);
        assertEquals(RESULT, expected);
        System.out.println(expected);
    }

    public static String concatLastNamesStage2(List<String> team) {
        if (team == null) {
            return "";
        }
        var output = new StringBuilder();
        for (String teamMemberName : team) {
            if (teamMemberName != null) {
                teamMemberName = teamMemberName.trim();
                if (!teamMemberName.isEmpty()) {
                    String lastName = extractLastName(teamMemberName);
                    output.append(lastName);
                    output.append(DELIMITER);
                }
            }
        }
        return output.toString();
    }

    @Test
    void testLastNameConcat() {
        final var expected = concatLastNames(TEAM);
        assertEquals(RESULT, expected);
        System.out.println(expected);
    }

    public static String concatLastNames(List<String> team) {
        if (team == null) {
            return "";
        }
        StringBuilder output = new StringBuilder();
        boolean isFirstFlag = true;
        for (String teamMemberName : team) { // HTD-1: Looping through the list
            if (teamMemberName != null) { // WTD-11: Deal with nulls
                teamMemberName = teamMemberName.trim(); // WTD-12: Deal with only white space names
                if (!teamMemberName.isEmpty()) { // WTD-13: Deal with empty names
                    if (!isFirstFlag) { // Catch: Should not prepend delimiter for first entry. 
                        output.append(DELIMITER);
                    }
                    String lastName = extractLastName(teamMemberName); // WTD-2: Extracting last name
                    output.append(lastName); // HTD-2: Aggregating the results with the delimiter.
                    isFirstFlag = false;
                }
            }
        }
        return output.toString();
    }

    public static String extractLastName(String fullName) {
        // Substring after last whitespace
        return fullName.substring(fullName.lastIndexOf(" ") + 1);
    }

}
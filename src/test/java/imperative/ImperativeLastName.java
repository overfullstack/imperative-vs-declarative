package imperative;/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

import common.Common;
import org.junit.jupiter.api.Test;

import java.util.List;

import static common.Common.DELIMITER;
import static common.Common.RESULT;
import static common.Common.TEAM;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImperativeLastName {

    @Test
    void testLastNameConcat() {
        final var expected = concatLastNames(TEAM);
        assertEquals(expected, RESULT);
        System.out.println(expected);
    }

    public static String concatLastNamesStage1(List<String> team) {
        var output = new StringBuilder();
        for (var teamMemberName : team) {
            var lastName = Common.extractLastName(teamMemberName.trim());
            output.append(lastName);
            output.append(DELIMITER);
        }
        return output.toString();
    }

    public static String concatLastNamesStage2(List<String> team) {
        if (team == null) {
            return "";
        }
        var output = new StringBuilder();
        for (var teamMemberName : team) {
            if (teamMemberName != null) {
                teamMemberName = teamMemberName.trim();
                if (!teamMemberName.isEmpty()) {
                    var lastName = Common.extractLastName(teamMemberName);
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
        var isFirst = true;
        for (var teamMemberName : team) { // HTD-1: Looping through the list
            if (teamMemberName != null) { // WTD-11: Deal with nulls
                teamMemberName = teamMemberName.trim(); // WTD-12: Deal with only white space names
                if (!teamMemberName.isEmpty()) { // WTD-13: Deal with empty names
                    if (!isFirst) { // Catch: Should not prepend delimiter for first entry. 
                        output.append(DELIMITER);
                    }
                    var lastName = Common.extractLastName(teamMemberName); // WTD-2: Extracting last name
                    output.append(lastName); // HTD-2: Aggregating the results with the delimiter.
                    isFirst = false;
                }
            }
        }
        return output.toString();
    }

}
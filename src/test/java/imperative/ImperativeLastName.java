package imperative;/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

import org.junit.jupiter.api.Test;

import java.util.List;

import static common.Common.DELIMITER;
import static common.Common.TEAM;
import static common.Common.result;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImperativeLastName {

    @Test
    void testLastNameFinderSimpleLoop() {
        final var expected = concatLastNames(TEAM);
        System.out.println(expected);
        assertEquals(expected, result);
    }

    public static String concatLastNames(List<String> team) {
        var output = new StringBuilder();
        var isFirst = true;
        for (var teamMemberName : team) { // Concern-1: Looping through the list
            if (teamMemberName != null && !teamMemberName.trim().isEmpty()) { // Catch-2: filter-out invalid names
                if (!isFirst) { // Catch-1: Should not prepend delimiter for first entry 
                    output.append(DELIMITER);
                }
                var lastName = extractLastName(teamMemberName); // Concern-2: Extracting last name
                output.append(lastName); // Concern-3: Aggregating the results with the delimiter.
                isFirst = false;
            }
        }
        return output.toString();
    }

    private static String extractLastName(final String fullName) {
        final var lastSpaceIndex = fullName.lastIndexOf(" "); // TODO 2019-06-27 gakshintala: fix
        final String lastName;
        if (lastSpaceIndex < 0) { // Catch-3: for single word names
            lastName = fullName;
        } else {
            lastName = fullName.substring(lastSpaceIndex + 1);
        }
        return lastName;
    }

}
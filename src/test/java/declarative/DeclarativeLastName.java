package declarative;/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static common.Common.DELIMITER;
import static common.Common.RESULT;
import static common.Common.TEAM;
import static java.util.function.Predicate.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeclarativeLastName {
    
    @Test
    void testLastNameFinderWithStream() {
        final var expected = concatLastNames(TEAM);
        final var expectedParallel = concatLastNamesInParallel(TEAM);
        assertEquals(expected, expectedParallel);
        assertEquals(expected, RESULT);
        System.out.println(expected);
    }

    private String concatLastNamesStage1(List<String> team) {
        return team.stream()
                .map(fullName -> fullName.substring(fullName.lastIndexOf(" ") + 1))
                .collect(Collectors.joining(DELIMITER));
    }

    private String concatLastNames(List<String> team) {
        return team.stream()                                // HTD-1: Looping through elements.
                .filter(Objects::nonNull)                                               // WTD-11: Deal with nulls.
                .map(String::trim)                                                      // WTD-12: Deal with only white space strings.
                .filter(not(String::isEmpty))                                           // WTD-13: Deal with empty strings.
                .map(fullName -> fullName.substring(fullName.lastIndexOf(" ") + 1)) // WTD-2: Extract Last Name.
                .collect(Collectors.joining(DELIMITER));    // HTD-2: Aggregating results.
    }
    
    private String concatLastNamesInParallel(List<String> team) {
        return team.parallelStream()
                .filter(Objects::nonNull) // Catch-1: Deal with nulls.
                .map(String::trim) // Catch-2: Deal with only white space strings.
                .filter(not(String::isEmpty)) // Catch-3: Deal with empty strings.
                .map(fullName -> fullName.substring(fullName.lastIndexOf(" ") + 1))
                .collect(Collectors.joining(DELIMITER));
    }

}
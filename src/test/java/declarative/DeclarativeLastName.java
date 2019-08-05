package declarative;/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

import common.Common;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static common.Common.DELIMITER;
import static common.Common.RESULT;
import static common.Common.TEAM;
import static java.util.function.Predicate.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeclarativeLastName {

    @Test
    void testLastNameConcat() {
        final var expected = concatLastNames(TEAM);
        final var expectedParallel = concatLastNamesInParallel(TEAM);
        assertEquals(expected, expectedParallel); // Parallel stream maintains the order
        assertEquals(expected, RESULT);
        System.out.println(expected);
    }

    @Test
    void testLastNameConcatForNullTeam() {
        final var expected = concatLastNames(null);
        final var expectedParallel = concatLastNamesInParallel(null);
        assertEquals(expected, expectedParallel);
        assertEquals(expected, "");
    }

    private String concatLastNamesStage1(List<String> team) {
        return team.stream()
                .map(Common::extractLastName)
                .collect(Collectors.joining(DELIMITER));
    }

    private String concatLastNames(List<String> team) {
        return Stream.ofNullable(team)                   // HTD-1: Looping through elements.
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)     // WTD-11: Deal with nulls.
                .map(String::trim)            // WTD-12: Deal with only white space strings.
                .filter(not(String::isEmpty)) // WTD-13: Deal with empty strings.
                .map(Common::extractLastName) // WTD-2: Extract Last Name.
                .collect(Collectors.joining(DELIMITER)); // HTD-2: Aggregating results.
    }

    private String concatLastNamesInParallel(List<String> team) {
        return Stream.ofNullable(team)
                .flatMap(Collection::parallelStream)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(not(String::isEmpty))
                .map(fullName -> fullName.substring(fullName.lastIndexOf(" ") + 1))
                .collect(Collectors.joining(DELIMITER));
    }

}
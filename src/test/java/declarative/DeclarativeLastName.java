package declarative;/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static common.Common.DELIMITER;
import static common.Common.TEAM;
import static common.Common.result;
import static java.util.function.Predicate.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeclarativeLastName {

    // First-Class Functions
    private static final BinaryOperator<String> LAST_WORD_REDUCER = (previous, current) -> current;
    private static final UnaryOperator<String> GET_LAST_NAME =
            fullName -> Arrays.stream(fullName.split("\\s+"))
                    .reduce(LAST_WORD_REDUCER)
                    .orElse("");

    @Test
    void testLastNameFinderWithStream() {
        final var expected = TEAM.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(not(String::isEmpty))
                .map(GET_LAST_NAME)
                .collect(Collectors.joining(DELIMITER));
        System.out.println(expected);
        assertEquals(expected, result);
    }

    @Test
    void testLastNameFinderWithParallelStream() {
        final var expected = TEAM.parallelStream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(not(String::isEmpty))
                .map(GET_LAST_NAME)
                .collect(Collectors.joining(DELIMITER));
        System.out.println(expected);
        assertEquals(expected, result);
    }
}
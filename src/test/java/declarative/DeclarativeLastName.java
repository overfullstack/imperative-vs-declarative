package declarative;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static common.Common.DELIMITER;
import static common.Common.EXPECTED_RESULT;
import static common.Common.TEAM;
import static java.util.function.Predicate.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeclarativeLastName {

    UnaryOperator<String> extractLastName = fullName ->
            fullName.substring(fullName.lastIndexOf(" ") + 1);

    @Test
    void testLastNameConcat() {
        final var expected = concatLastNames(TEAM);
        final var expectedParallel = concatLastNamesInParallel(TEAM);
        assertEquals(expected, expectedParallel); // Parallel stream maintains the order
        assertEquals(EXPECTED_RESULT, expected);
        System.out.println(expected);
    }

    @Test
    void testLastNameConcatForNullTeam() {
        final var expected = concatLastNames(null);
        final var expectedParallel = concatLastNamesInParallel(null);
        assertEquals(expected, expectedParallel);
        assertEquals("", expected);
    }

    private String concatLastNamesStage1(List<String> team) {
        return team.stream()
                .map(extractLastName)
                .collect(Collectors.joining(DELIMITER));
    }

    /**
     * ∙ Patching up -> Extending<br>
     * ∙ Control Statements -> Expressions<br>
     * ∙ Code that talks<br>
     * ∙ Puzzle pieces fit together.<br>
     */
    public String concatLastNames(List<String> team) {
        return Stream.ofNullable(team)                   // HTD-1: Looping through elements.
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)     // WTD-11: Deal with nulls.
                .map(String::trim)            // WTD-12: Deal with only-white-space strings.
                .filter(not(String::isEmpty)) // WTD-13: Deal with empty strings.
                .map(extractLastName) // WTD-2: Extract Last Name.
                .collect(Collectors.joining(DELIMITER)); // HTD-2: Aggregating results.
    }

    public String concatLastNamesInParallel(List<String> team) {
        return Stream.ofNullable(team)
                .flatMap(Collection::parallelStream)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(not(String::isEmpty))
                .map(extractLastName)
                .collect(Collectors.joining(DELIMITER));
    }

}

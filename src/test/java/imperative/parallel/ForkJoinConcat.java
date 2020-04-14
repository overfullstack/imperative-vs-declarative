package imperative.parallel;/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import static common.Common.EXPECTED_RESULT;
import static common.Common.TEAM;
import static imperative.ImperativeLastName.concatLastNames;
import static imperative.parallel.Util.AVAILABLE_CORES;
import static imperative.parallel.Util.concatResults;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ForkJoinConcat {
    
    @Test
    void parallelWithForkJoinPool() {
        var forkJoinPool = new ForkJoinPool(AVAILABLE_CORES);
        var actualResult = forkJoinPool.invoke(new MyRecursiveTask(TEAM));
        System.out.println(actualResult);
        assertEquals(EXPECTED_RESULT, actualResult);
    }

    static class MyRecursiveTask extends RecursiveTask<String> {
        private static final long serialVersionUID = -5978274303314688527L;

        private static final int MIN_TEAM_SIZE = 2; // In real-world, DO NOT have it below 10,000
        private final List<String> team;

        MyRecursiveTask(List<String> team) {
            this.team = team;
        }

        @Override
        protected String compute() {
            if (team.size() > MIN_TEAM_SIZE) {
                var mid = team.size() / 2;
                final var myRecursiveTask1 = new MyRecursiveTask(team.subList(0, mid));
                final var myRecursiveTask2 = new MyRecursiveTask(team.subList(mid, team.size()));

                myRecursiveTask1.fork();
                myRecursiveTask2.fork();

                var results = new ArrayList<String>();
                results.add(myRecursiveTask1.join());
                results.add(myRecursiveTask2.join());
                return concatResults(results);
            } else {
                return concatLastNames(team);
            }
        }
    }

}

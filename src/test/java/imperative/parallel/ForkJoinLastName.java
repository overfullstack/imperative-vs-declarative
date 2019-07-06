package imperative.parallel;/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import static common.Common.TEAM;
import static imperative.ImperativeLastName.concatLastNames;

public class ForkJoinLastName {

    private static final int AVAILABLE_CORES = Runtime.getRuntime().availableProcessors();

    @Test
    void parallelWithForkJoinPool() {
        var forkJoinPool = new ForkJoinPool(AVAILABLE_CORES);
        var results = forkJoinPool.invoke(new MyRecursiveTask(TEAM));
        System.out.println(Util.concatResults(results));
    }

    static class MyRecursiveTask extends RecursiveTask<List<String>> {
        private static final long serialVersionUID = -5978274303314688527L;
        
        private static final int MIN_TEAM_SIZE = 2; // In real-world, DO NOT have it below 10,000
        private final List<String> team;

        MyRecursiveTask(List<String> team) {
            this.team = team;
        }

        @Override
        protected List<String> compute() {
            if (team.size() > MIN_TEAM_SIZE) {
                var mid = team.size() / 2;
                final var myRecursiveTask1 = new MyRecursiveTask(team.subList(0, mid));
                final var myRecursiveTask2 = new MyRecursiveTask(team.subList(mid, team.size()));

                myRecursiveTask1.fork();
                myRecursiveTask2.fork();

                var results = new ArrayList<String>();
                results.addAll(myRecursiveTask1.join());
                results.addAll(myRecursiveTask2.join());
                return results;
            } else {
                return Collections.singletonList(concatLastNames(team));
            }
        }
    }

}
package imperative.parallel;/*
 * Copyright (c) 2019 - Present, Gopal S Akshintala
 * This source code is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * 	http://creativecommons.org/licenses/by-sa/4.0/
 */

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static common.Common.TEAM;
import static imperative.ImperativeLastName.concatLastNames;
import static imperative.parallel.Util.concatResults;

public class ThreadPoolLastName {

    private static final int AVAILABLE_CORES = Runtime.getRuntime().availableProcessors();

    @Test
    void testLastNameFinderThreadPool() {
        System.out.println(parallelWithThreadPool(TEAM));
    }

    private String parallelWithThreadPool(List<String> team) {
        var exec = Executors.newFixedThreadPool(AVAILABLE_CORES - 1);
        var futureList = new ArrayList<Future<String>>();
        var segmentLen = team.size() / AVAILABLE_CORES;
        if (segmentLen == 0) {
            segmentLen = team.size();
        }
        var offset = 0;
        while (offset < team.size()) {
            final var from = offset;
            final var to = offset + segmentLen;
            futureList.add(exec.submit(new Callable<>() {
                @Override
                public String call() {
                    return concatLastNames(team.subList(from, to));
                }
            }));
            offset += segmentLen;
        }
        var results = new ArrayList<String>();
        for (var future : futureList) {
            try {
                results.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        if (offset < team.size()) {
            results.add(concatLastNames(team.subList(team.size() - segmentLen, team.size())));
        }

        exec.shutdown();
        try {
            exec.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return concatResults(results);
    }

}
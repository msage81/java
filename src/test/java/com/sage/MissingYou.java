package com.sage;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertEquals;

public class MissingYou {

    private List<Integer> smallRange;
    private List<Integer> largeRange;

    @Before
    public void setup(){
        smallRange = new ArrayList();
        smallRange.add(100);
        smallRange.add(110);

        largeRange = IntStream.rangeClosed(1, 100_000).filter((i) -> i % 5000 != 0).boxed().collect(Collectors.<Integer>toList());

    }
    @Test
    public void missingYou(){
        List<Integer> missing = missing(smallRange);
        assetEachInteger(missing);
    }

    @Test
    public void missingYouTiming(){
        long start = System.currentTimeMillis();
        List<Integer> missing = missing(largeRange);
        System.out.format("Total time for non-optimized solution is %d milliseconds",(System.currentTimeMillis() - start));
        assertEquals(19, missing.size());
    }

    @Test
    public void missingYouOptimized(){
        List<Integer> missing = missingOptimized(smallRange);
        assetEachInteger(missing);
    }
    @Test
    public void missingYouTimingOptimized(){
        long start = System.currentTimeMillis();
        List<Integer> missing = missingOptimized(largeRange);
        System.out.format("Total time for optimized solution is %d milliseconds",(System.currentTimeMillis() - start));
        assertEquals(99998, missing.size());
    }

    public List<Integer> missing(List<Integer> ints){
        int min = ints.get(0);
        int max = ints.get(ints.size() - 1);
        List<Integer> allInts = IntStream.rangeClosed(min, max).boxed().collect(Collectors.<Integer>toList());
        allInts.removeAll(ints);
        return allInts;
    }
    public List<Integer> missingOptimized(List<Integer> ints){
        List<Integer> missing = new ArrayList<>();
        int min = ints.get(0);
        int max = ints.get(ints.size() - 1);
        final IntWrapper intWrapper = new IntWrapper(min);
        IntStream.rangeClosed(min, max).boxed().forEach((i) -> {
            if(i != intWrapper.index){
                while(intWrapper.index != i){
                    missing.add(i);
                    intWrapper.index++;
                }
            }
        });
        return missing;

    }
    class IntWrapper{

        int index;
        IntWrapper(int index){
            this.index = index;
        }
    }

    private void assetEachInteger(List<Integer> missing) {
        Iterator<Integer> itMissing = missing.iterator();
        IntStream.rangeClosed(101,109).forEach((i) -> assertEquals(Optional.ofNullable(i), Optional.of(itMissing.next())));
    }
    private void timingAssertion(String solutionType, Function<List<Integer>,List<Integer>> func){
        long start = System.currentTimeMillis();
        List<Integer> missing = func.apply(largeRange);
        System.out.format("Total time for optimized solution is %d milliseconds",(System.currentTimeMillis() - start));
        assertEquals(99998, missing.size());

    }


}

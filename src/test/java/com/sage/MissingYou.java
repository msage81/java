package com.sage;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertEquals;

public class MissingYou {

    private List<Integer> smallRange;
    private List<Integer> skipEvery5000;

    @Before
    public void setup(){
        smallRange = new ArrayList();
        smallRange.add(100);
        smallRange.add(105);
        smallRange.add(110);

        skipEvery5000 = IntStream.rangeClosed(1, 100_000).filter((i) -> i % 5000 != 0).boxed().collect(Collectors.<Integer>toList());

    }

    Function<List<Integer>,List<Integer>> missing = (List<Integer> ints) -> {
        int min = ints.get(0);
        int max = ints.get(ints.size() - 1);
        List<Integer> allInts = IntStream.rangeClosed(min, max).boxed().collect(Collectors.<Integer>toList());
        allInts.removeAll(ints);
        return allInts;
    };

    @Test
    public void missingYou(){
        List<Integer> miss = missing.apply(smallRange);
        assetEachInteger(miss);
    }

    @Test
    public void missingYouTiming(){
        timingAssertion("non-optimized",missing);
    }

    Function<List<Integer>,List<Integer>> missingOptimized = (List<Integer> ints) -> {
        List<Integer> missing = new ArrayList<>();
        if(ints == null || ints.isEmpty())return missing;
        final IntWrapper intWrapper = new IntWrapper(ints.get(0));
        ints.stream().forEach((i) -> {
            if(i != intWrapper.index){
                while(intWrapper.index != i){
                    missing.add(intWrapper.index);
                    intWrapper.index++;
                }
                intWrapper.index++;
            }else{
                intWrapper.index++;
            }
        });
        return missing;
    };
    class IntWrapper{

        int index;
        IntWrapper(int index){
            this.index = index;
        }
    }

    @Test
    public void missingYouOptimized(){
        List<Integer> miss = missingOptimized.apply(smallRange);
        assetEachInteger(miss);
    }
    @Test
    public void missingYouOptimizedNoneMissing(){
        List<Integer> miss = missingOptimized.apply(IntStream.rangeClosed(1,100).boxed().collect(Collectors.toList()));
        assertEquals(0,miss.size());
    }
    @Test
    public void missingYouOptimizedEmptyList(){
        List<Integer> miss = missingOptimized.apply(new ArrayList<>());
        assertEquals(0,miss.size());
    }
    @Test
    public void missingYouOptimizedNullArgument(){
        List<Integer> miss = missingOptimized.apply(null);
        assertEquals(0,miss.size());
    }
    @Test
    public void missingYouOptimizedOneElement(){
        List<Integer> l = new ArrayList<>();
        l.add(1);
        List<Integer> miss = missingOptimized.apply(l);
        assertEquals(0,miss.size());
    }
    @Test
    public void missingYouTimingOptimized(){
        timingAssertion("optimized",missingOptimized);
    }


    private void assetEachInteger(List<Integer> missing) {
        assertEquals(8,missing.size());
        assertEquals(new Integer(101),missing.get(0));
        assertEquals(new Integer(102),missing.get(1));
        assertEquals(new Integer(103),missing.get(2));
        assertEquals(new Integer(104),missing.get(3));
        assertEquals(new Integer(106),missing.get(4));
        assertEquals(new Integer(107),missing.get(5));
        assertEquals(new Integer(108),missing.get(6));
        assertEquals(new Integer(109),missing.get(7));

    }
    private void timingAssertion(String solutionType, Function<List<Integer>,List<Integer>> func){
        long start = System.currentTimeMillis();
        List<Integer> missing = func.apply(skipEvery5000);
        System.out.format("Total time for %s solution is %d milliseconds\n",solutionType,(System.currentTimeMillis() - start));
        assertEquals(19, missing.size());

    }


}

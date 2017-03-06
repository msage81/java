package com.sage.future;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FutureTest {

  @Before
  public void setup(){
    System.out.printf("Number of processors is %d",Runtime.getRuntime().availableProcessors());
  }
  @Test
  public void completeMeParallel(){
    long start = System.currentTimeMillis();
    IntStream.range(0,10).parallel().forEach(i -> new DoSomething().getInt());
    System.out.printf("Total time for parallel is %f seconds",(System.currentTimeMillis() - start)/1000f);
  }
  @Test
  public void completeMeFuture(){
    long start = System.currentTimeMillis();
    Stream<CompletableFuture<Integer>> completableFutureStream = IntStream.range(0, 10).boxed().map(i -> CompletableFuture.supplyAsync(() -> new DoSomething().getInt()));
    completableFutureStream.
    System.out.printf("Total time for CompletableFuture is %f seconds",(System.currentTimeMillis() - start)/1000f);
  }

  @Test
  public void completeMeSequential(){
    long start = System.currentTimeMillis();
    IntStream.range(0,10).forEach(i -> new DoSomething().getInt());
    System.out.printf("Total time for parallel is %f seconds",(System.currentTimeMillis() - start)/1000f);
  }

  class DoSomething{
    public int getInt(){
      delay();
      int i = new Random().nextInt(1000);
      return i;
    }
  }
  private void delay(){
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

package com.sage.stream;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamTest {

  private List<Integer> ints;

  @Before
  public void setUp(){
    ints = IntStream.rangeClosed(1,100).boxed().collect(Collectors.toList());
  }
  @Test
  public void filter(){
    List<Integer> result = ints.stream().filter(i -> i > 50).collect(Collectors.toList());
    assertEquals(50,result.size());
  }
  @Test
  public void reduce(){
    int result = ints.stream().limit(5).reduce(0, (i, j) -> i + j).intValue();
    assertEquals(15,result);
  }
}

package sage.stream;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ObjectFactory {
  public static List<Integer> generateRandom(int howMany,int low, int high){
    return IntStream.range(0,howMany).boxed().map(i ->
            ThreadLocalRandom.current().nextInt(low, high + 1)).collect(Collectors.toList());

  }
}

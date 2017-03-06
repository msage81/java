package sage.lamda;

import java.util.Comparator;
import java.util.function.Consumer;

import java.util.function.Supplier;

public class ValidLamdas {
    public void supplier(){
        Supplier<String> supplier = () -> "supplied";
        System.out.println(supplier.get());
    }
    public void consume(){
        Consumer<String> consumer = (String s)->System.out.printf("Consumed %s\n",s);
        consumer.accept("it");
    }
    public void comparator(){
        Comparator<String> comparator = (String s1, String s2)->s1.compareTo(s2);
        System.out.printf("%s compared to %s results is %d\n","hello","there",comparator.compare("hello","there"));
    }
    public void function( MyFunction function){
        System.out.println(function.makeStringOfInts(1,2,367,78));
    }
    public static void main(String[] args) {
        ValidLamdas v = new ValidLamdas();
        v.supplier();
        v.consume();
        v.comparator();
        v.function((Integer ... ints)-> {
            StringBuilder sb = new StringBuilder();
            for (Integer i:ints) {
                sb.append(i);
            }
            return sb.toString();
        });
    }
}

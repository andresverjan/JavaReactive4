package listas;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main (String [] Args){
        List<String> nombres = Arrays.asList("Juan", "Ana", "Carlos", "Maria");

        nombres.stream().sorted().forEach(System.out::println);

    }



}

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        char[] arrayS = {'h', 'e', 'l', 'l', 'o'};
        char[] arrayS2 = {'H', 'a', 'n', 'n', 'a' ,'h'};
        printReverse(arrayS);
        printReverse(arrayS2);
    }
    public static void printReverse(char[] array) {
        for (int x = array.length - 1; x >= 0; x--) {
            System.out.print(array[x]);
        }
        System.out.println();
    }

    public static void printReversev2(char[] array) {
        Collections.reverse(Arrays.asList(a));
        System.out.println();
    }
}


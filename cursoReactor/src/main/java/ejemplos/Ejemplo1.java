package ejemplos;

public class Ejemplo1 {
    public static void main(String[] args) {
        String s = "hello";
        String sr = "";
        StringBuilder textReverse =  new StringBuilder();
        for (int i = s.length(); i > 0; i--){
            textReverse.append(s.substring(i-1,i));
            sr = sr.concat(s.substring(i-1,i));
        }
        System.out.println(textReverse);
        System.out.println(sr);
        System.out.println(new StringBuilder(s).reverse());
    }
}
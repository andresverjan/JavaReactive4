package clases;

public class Subscriber1 implements Observer{
    @Override
    public void update(int number) {
        if (number > 10) {
            System.out.println("Subscriber1: Raíz cuadrada de " + number + " es " + Math.sqrt(number));
        }
    }


}

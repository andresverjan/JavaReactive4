package clases;

public class Subscriber2 implements Observer{
    @Override
    public void update(int number) {
        if (number > 20) {
            System.out.println("Subscriber2: Raíz cuadrada de " + number + " es " + Math.sqrt(number));
        }
    }
}

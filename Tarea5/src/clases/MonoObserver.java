package clases;

import clases.Observer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonoObserver {
    private List<Observer> observers = new ArrayList<>();
    private Random random = new Random();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void generateNumber() {
        int number = random.nextInt(100); // Genera un número aleatorio entre 0 y 99
        System.out.println("Generated number: " + number);
        notifyObservers(number);
    }

    private void notifyObservers(int number) {
        for (Observer observer : observers) {
            observer.update(number);
        }
    }
}

package com.curso_java.ejercicio_2;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.observerMethod();
    }
    public void observerMethod() {

        Observable<Integer> observable = Observable.range(1, 10);

        // Observador 1
        Observer<Integer> observer1 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Observer 1 Subscribed -> Incrementar numeros");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println(value + 1);
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Observer 1 Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Observer 1 Completed");
            }
        };

        // Observador 2
        Observer<Integer> observer2 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Observer 2 Subscribed -> Factorial de los numeros");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("Factorial de: " + value + " -> " + factorial(value));
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Observer 2 Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Observer 2 Completed");
            }
        };

        // Observador 3
        Observer<Integer> observer3 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Observer 3 Subscribed -> Determinar si el numero es par");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println(value + ": " + (esPar(value) ? "Es par" : "No es par"));
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Observer 3 Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Observer 3 Completed");
            }
        };

        // Suscribir el observable a cada uno de los observadores
        observable.subscribe(observer1);
        observable.subscribe(observer2);
        observable.subscribe(observer3);
    }

    public Integer factorial(Integer value) {
        if (value > 1) {
            return value * factorial(value - 1);
        }
        return value;
    }

    public boolean esPar(Integer value) {
        return value % 2 == 0;
    }
}

package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class Main {
    public static void main(String[] args) {
        Observable<Integer> observable = Observable.range(1, 5);

        Observer<Integer> observer = new Observer<Integer>() {

            @Override

            public void onSubscribe(Disposable d) {

                System.out.println("Subscribed");

            }



            @Override

            public void onNext(Integer value) {

                System.out.println("Received: " + value);

                //factorial(value);

            }



            @Override

            public void onError(Throwable e) {

                System.err.println("Error: " + e.getMessage());

            }



            @Override

            public void onComplete() {

                System.out.println("Completed");

            }

        };

// Suscribir el observable al observador

        observable.subscribe(observer);

        //observable.unsubscribe(observer);

    }
}
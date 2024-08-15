package org.example;


import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class Main {
    public static void main(String[] args) {
        Observable<Integer> observable = Observable.range(1, 10);

        Observer<Integer> observer1 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Observer1 Subscribed!");
            }
            @Override
            public void onNext(Integer value) {
                System.out.println("Observer1 Received: " + (value + 1));
            }
            @Override
            public void onError(Throwable e) {
                System.out.println("Observer1 Error: " + e.getMessage());
            }
            @Override
            public void onComplete() {
                System.out.println("Observer1 Completed!");
            }
        };

        Observer<Integer> observer2 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Observer2 Subscribed!");
            }
            @Override
            public void onNext(Integer value) {
                int factorial = 1;
                for (int i = 1; i <= value; i++) {
                    factorial *= i;
                }
                System.out.println("Observer2 Received: " + value + " Factorial: " + factorial);
            }
            @Override
            public void onError(Throwable e) {
                System.out.println("Observer2 Error: " + e.getMessage());
            }
            @Override
            public void onComplete() {
                System.out.println("Observer2 Completed!");
            }
        };

        Observer<Integer> observer3 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Observer3 Subscribed!");
            }
            @Override
            public void onNext(Integer value) {
                System.out.println("Observer3 Received of " + value + " Multiplicacion: " + (value * value));
            }
            @Override
            public void onError(Throwable e) {
                System.out.println("Observer3 Error: " + e.getMessage());
            }
            @Override
            public void onComplete() {
                System.out.println("Observer3 Completed!");
            }
        };

        observable.subscribe(observer1);
        observable.subscribe(observer2);
        observable.subscribe(observer3);
    }


}
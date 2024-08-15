package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class Main {
    public static void main(String[] args) {
        Observable<Integer> observable = Observable.range(1, 10);

        Observer<Integer> observerIncremental = new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed |");
            }


            @Override
            public void onNext(Integer value) {
                System.out.println("Received: " + value);
            }


            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }


            @Override
            public void onComplete() {
                System.out.println("Completed 1");
            }

        };

        Observer<Integer> observerFactorial = new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed 2");
            }


            @Override
            public void onNext(Integer value) {
                long factorialValue = factorial(value);
                System.out.println("Received: " + factorialValue);
            }


            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }


            @Override
            public void onComplete() {
                System.out.println("Completed 2");
            }

        };

        Observer<Integer> observerPow = new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed 3");
            }


            @Override
            public void onNext(Integer value) {
                System.out.println("Received: " + Math.pow(value,2));
            }


            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }


            @Override
            public void onComplete() {
                System.out.println("Completed 3");
            }

        };

        observable.subscribe(observerIncremental);
        observable.subscribe(observerFactorial);
        observable.subscribe(observerPow);
        //observable.unsubscribe(observer);



    }

    public static long  factorial(Integer value){
        long result = 1;
        for (int i = 1; i <= value; i++) {
            result*=i;
        }
        return result;
    }

}
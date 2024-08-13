package org.example.observable;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class ObservableApplication {

    public static int factorial(int n){
        if (n == 0)
            return 1;
        else
            return(n * factorial(n-1));
    }


    public static void main(String[] args) {
        Observable<Integer> observable = Observable.range(1, 10);

        Observer<Integer> observer = new Observer<>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribed");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("Increment -> "+ integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error " + e);
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };

        Observer<Integer> observerPow = new Observer<>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribed");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println(integer + " raised to 2 -> "  + Math.pow(integer.doubleValue(), 2.0));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error " + e);
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };

        Observer<Integer> factorialObserver = new Observer<>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribed");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("Factorial of " + integer + " -> "+ factorial(integer));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error " + e);
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };

        observable.subscribe(observer);
        observable.subscribe(observerPow);
        observable.subscribe(factorialObserver);
    }

}

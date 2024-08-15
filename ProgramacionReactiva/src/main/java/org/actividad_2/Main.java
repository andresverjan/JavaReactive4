package org.actividad_2;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.apache.commons.math3.util.CombinatoricsUtils;

public class Main {

    public static void main(String[] args) {
        Observable<Integer> observable = Observable.range(1,10);

        Observer<Integer> observer1 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d){
                System.out.println("Subscribed Observer 1");
            }

            @Override
            public void onNext(Integer integer){
                System.out.println("Received: " + integer);
                System.out.println("Return: " + (integer + 1));
            }

            @Override
            public void onError(Throwable e){
                System.out.println("Error");
            }

            @Override
            public void onComplete() {
                System.out.println("Completed observer 1");
            }
        };

        Observer<Integer> observer2 = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribed Observer 2");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("Received: " + integer);
                System.out.println("Return factorial: " + CombinatoricsUtils.factorial(integer));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error");

            }

            @Override
            public void onComplete() {
                System.out.println("Completed observer 2");

            }
        };

        Observer<Integer> observer3 = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribed Observer 3");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("Received: " + integer);
                System.out.println("Return " + integer + "^2: " + integer * integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error");
            }

            @Override
            public void onComplete() {
                System.out.println("Completed observer 3");
            }
        };
        observable.subscribe(observer1);
        observable.subscribe(observer2);
        observable.subscribe(observer3);

    }
}
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
                System.out.println("Subscribed observer 1");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("Observer 1 Received: " + value);
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed observer 1");
            }
        };
        //observador 2
        Observer<Integer> observer2 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed observer 2");
            }

            @Override
            public void onNext(Integer value) {
                factorial(value);
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed observer 2");
            }
        };
        //observador 3
        Observer<Integer> observer3 = new Observer<Integer>() {
            String concatenar = "";
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed observer 3");
            }

            @Override
            public void onNext(Integer value) {


                concatenar+=value;
                System.out.println("Observer 3 Received: " + concatenar);

            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed observer 3");
            }
        };
        observable.subscribe(observer1);
        observable.subscribe(observer2);
        observable.subscribe(observer3);
        //observable.unsubscibre(observer);

    }

    public static void  factorial(int number){
        long result = 1;
        for (int i = 1; i <= number; i++) {
            result*=i;
        }
        System.out.println("factorial numero "+number+" -> factorial= "+result);
    }


}
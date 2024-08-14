package org.cursoReactividad.Ejercicio2;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


public class Main {
    public static void main(String[] args) {
        Observable<Integer> observable =Observable.range(1,10);
        Observer<Integer> observer=new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Suscribed observer 1");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("Automatic Increase "+integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error: "+e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed observer 1");
            }
        };
        Observer<Integer> observer2=new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Suscribed observer 2");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("Factorial of "+integer+": "+calculateFact(integer));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error: "+e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed observer 2");
            }
        };
        Observer<Integer> observer3=new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Suscribed observer3");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("the number "+integer+": "+primeNumber(integer));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error: "+e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed observer 3");
            }
        };

        observable.subscribe(observer);
        observable.subscribe(observer2);
        observable.subscribe(observer3);
    }

    public static int calculateFact (int number){
        int i,fact=1;
        for(i=1;i<=number;i++){
            fact=fact*i;
        }
        return fact;
    }
    public static String primeNumber(int num) {
        boolean prime = true;
        String isPrime="";
        for(int i = 2; i < num; i++) {
            if (num % i == 0) {
                prime = false;
                break;
            }
        }
        if (prime){
           isPrime="The number is prime.";

        }
        else{
           isPrime= "The number isn't prime.";

        }
        return isPrime;
    }
}
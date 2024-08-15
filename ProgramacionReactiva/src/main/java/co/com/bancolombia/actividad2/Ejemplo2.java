package co.com.bancolombia.actividad2;


import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class Ejemplo2 {



    public static void main(String[] args) {
        Observable<Integer> observable = Observable.range(1,10);
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d){
                System.out.println("Subscribed Observer 1");
            }

            @Override
            public void onNext(Integer value){
                System.out.println("Received: " + value);
                value= value+1;
               System.out.println("inc: " + value);
            }

            @Override
            public void onError(Throwable e){
                System.out.println("Error");
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };

        Observer<Integer> observer1 = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribed Observer 2");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("Received: " + integer);
                long factorial = 1;

                for (int i = 1; i <= integer; i++) {
                    factorial *= i;
                }

                System.out.println("Factorial: " + factorial);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error");

            }

            @Override
            public void onComplete() {
                System.out.println("Completed");

            }
        };

        Observer<Integer> observer2 = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Observer 2");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("Received: " + integer);
                System.out.println("out *2 " + integer*2);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error");
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };

        observable.subscribe(observer);
        observable.subscribe(observer1);
        observable.subscribe(observer2);

    }








}

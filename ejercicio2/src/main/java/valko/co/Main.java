package valko.co;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class Main {

    public static void main(String[] args) {

        Observable<Integer> observable = Observable.range(1, 10);

        Observer<Integer> observer1 = new Observer<>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Observer 1 Subscribed");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("Observer 1 - Autoincrement: " + (value + 1));
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Observer 1 - Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Observer 1 - Completed");
            }
        };

        Observer<Integer> observer2 = new Observer<>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Observer 2 Subscribed");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("Observer 2 - Factorial: " + factorial(value));
            }

            private int factorial(int n) {
                if (n == 0) {
                    return 1;
                }
                int fact = 1;
                for (int i = 1; i <= n; i++) {
                    fact *= i;
                }
                return fact;
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Observer 2 - Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Observer 2 - Completed");
            }
        };

        Observer<Integer> observer3 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Observer 3 Subscribed");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("Observer 3 - Cuadrado: " + (value * value));
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Observer 3 - Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Observer 3 - Completed");
            }
        };

        observable.subscribe(observer1);
        observable.subscribe(observer2);
        observable.subscribe(observer3);
    }
}
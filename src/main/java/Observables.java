import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.Observable;
import java.util.Observer;

public class Observables {
    public static void main(String[] args) {
        Observable observable = Observable.range(1, 10);

        // Observer 1 -> Autoincrementa los números
        Observer<Integer> autoIncrementingObserver = new Observer<Integer>() {
            private int currentValue = 0;

            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed");
            }

            @Override
            public void onNext(Integer value) {
                currentValue = value + 1;
                System.out.println("Auto-incremented Value: " + currentValue);
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Observer 1 Completed");
            }
        };

        // Observable 2 -> Calcula el factorial
        Observer<Integer> factorialObserver = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("Factorial de " + value + " es: " + factorial(value));
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Observer 2 Completed");
            }

            private long factorial(int num) {
                long result = 1;
                for (int i = 1; i <= num; i++) {
                    result *= i;
                }
                return result;
            }
        };

        // Observador 3 -> diferente
        Observer<Integer> differentCalculationObserver = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Observer 3 Subscribed");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("El cuadrado de " + value + " es: " + (value * value));
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Observer 3 Completed");
            }
        };

        // Suscribir los observadores al observable
        System.out.println("Suscribiendo Observers...\n");
        observable.subscribe(autoIncrementingObserver);
        observable.subscribe(factorialObserver);
        observable.subscribe(differentCalculationObserver);
    }
}

package Ejercicio3;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class EjercicioObserver {

    public static void main(String[] args) {

        // Crear un Observable que emite números del 1 al 10
        Observable<Integer> observable = Observable.range(1, 10);

        // Primer Observer: Autoincrementa los números
        Observer<Integer> observer1 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Observer 1 Subscribed");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("Observer 1 - Autoincrementado: " + (value-1 + 1));
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Observer 1 Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Observer 1 Completed");
            }
        };

        // Segundo Observer: Calcula el factorial de los números
        Observer<Integer> observer2 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Observer 2 Subscribed");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("Observer 2 - Factorial: " + factorial(value));
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Observer 2 Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Observer 2 Completed");
            }
        };

        // Tercer Observer: Eleva los números al cuadrado
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
                System.err.println("Observer 3 Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Observer 3 Completed");
            }
        };

        // Suscribir los Observers al Observable
        observable.subscribe(observer1);
        observable.subscribe(observer2);
        observable.subscribe(observer3);
    }

    // Calcular el factorial
    private static int factorial(int n) {
        if (n == 0) return 1;
        return n * factorial(n - 1);
    }

}


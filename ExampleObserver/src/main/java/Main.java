import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.operators.observable.ObservableRange;

public class Main {
    public static void main(String[]args){
        Observable<Integer> observable = ObservableRange.range(1,10);

        Observer<Integer> observerIncremento = new Observer<>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("-- observerIncremento --");
                System.out.println("-> Subscribed");
            }

            @Override
            public void onNext(@NonNull Integer value) {
                int incremento = value + 1;
                System.out.println("Incremento de " + value + " es " + incremento);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("-> Completed");
            }
        };

        Observer<Integer> observerFactorial = new Observer<>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("-- observerFactorial --");
                System.out.println("-> Subscribed");
            }

            @Override
            public void onNext(@NonNull Integer value) {
                System.out.println("Factorial de " + value + " es " + this.factorial(value));
            }

            private long factorial(Integer numero) {
                long factor = 1;

                for(int i = 1; i <= numero ; i++) {
                    factor *= i;
                }
                return factor;
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("-> Completed");
            }
        };

        Observer<Integer> observerSumatoria = new Observer<>() {
           long suma = 0;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("-- observerFactorial --");
                System.out.println("-> Subscribed");
            }

            @Override
            public void onNext(@NonNull Integer value) {
                suma += value;
                System.out.println(" -> Received: " + value);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("La suma de los valores recibidos es: " + suma);
                System.out.println("-> Completed");
            }
        };

        observable.subscribe(observerIncremento);
        observable.subscribe(observerFactorial);
        observable.subscribe(observerSumatoria);
    }
}

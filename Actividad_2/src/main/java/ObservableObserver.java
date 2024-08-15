import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


public class ObservableObserver {

    public static void main(String[] args) {
        Observable<Integer> observable = Observable.range(1,10);

        Observer observer = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribed");
            }
            @Override
            public void onNext(Object value) {
                System.out.println("Received: " + value);
            }
            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error: " + e.getMessage());
            }
            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };

        Observer observer1 = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribed");
            }
            @Override
            public void onNext(Object value) {
                System.out.println("Received: " + value);
            }
            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error: " + e.getMessage());
            }
            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };

        Observer observer2 = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribed");
            }
            @Override
            public void onNext(Object value) {
                factorial(value);
            }
            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error: " + e.getMessage());
            }
            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };

        Observer observer3 = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribed");
            }
            @Override
            public void onNext(Object value) {
                System.out.println("Valor multiplicado por 2: " + (int)value*2);
            }
            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("Error: " + e.getMessage());
            }
            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };

        observable.subscribe(observer);
        observable.subscribe(observer1);
        observable.subscribe(observer2);
        observable.subscribe(observer3);
    }

    private static void factorial(Object value) {
        int valor = (int) value;

        int fact = 1;
        for (int i = 2; i <= valor; i++) {
            fact = fact * i;
        }
        System.out.println("Factorial de: " + valor + " = " + fact);
    }
}

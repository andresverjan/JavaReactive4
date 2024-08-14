import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Main {
    public static void main(String[] args) {
        Observable<Integer> observable = Observable.range(1, 10);

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("Received: " + value);
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };



    Observer<Integer> observer2 = new Observer<Integer>() {
        @Override
        public void onSubscribe(Disposable d) {
            System.out.println("Subscribed observer2");
        }

        @Override
        public void onNext(Integer value) {
            System.out.println("Received on observer2: " + value);
            System.out.println("Factorial observer: " + factorial(value));
        }

        @Override
        public void onError(Throwable e) {
            System.err.println("Error: " + e.getMessage());
        }

        @Override
        public void onComplete() {
            System.out.println("Completed");
        }
    };

        Observer<Integer> observer3 = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("Subscribed observer2");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("Received on observer3: " + value);
                System.out.println("Sum of digits observer: " + sumOfDigits(value));
            }

            @Override
            public void onError(Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };
    // Suscribir el observable al observador
        observable.subscribe(observer);
        observable.subscribe(observer2);
        observable.subscribe(observer3);

}

    public static int sumOfDigits(int number) {
        int sum = 0;
        while (number != 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }

    public static int factorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }
}

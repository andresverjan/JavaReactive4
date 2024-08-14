import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class main {

    public static  void main(String[] args){
        Observable<Integer> observable = Observable.range(1,10);

        Observer<Integer> observer1 = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribed Obs #1");
            }

            @Override
            public void onNext(@NonNull Integer value) {
                System.out.println("Received: "+(value+1));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed #1");
            }
        };

        Observer<Integer> observer2 = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribed Obs #2");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("Received #2: "+integer);
                System.out.println("fact: "+factorial(integer));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed #2");
            }
        };

        Observer<Integer> observer3 = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribed Obs #3");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("OK: "+integer*5);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.err.println("Error: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Completed #3");
            }
        };

       observable.subscribe(observer1);
       observable.subscribe(observer2);
       observable.subscribe(observer3);


    }

    public static Integer factorial(Integer n){
        Integer count=1;
        for(int i=0;i<n;i++){
            count = count + count*i;
        }
        return count;
    }



}

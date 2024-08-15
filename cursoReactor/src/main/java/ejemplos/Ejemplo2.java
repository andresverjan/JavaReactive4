package ejemplos;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class Ejemplo2 {
    public static int factorial(int n){
        if (n == 0 || n == 1){
            return 1;
        }else{
            return n * factorial(n - 1);
        }
    }
    public static int sumatoriaN(int n){
        if (n == 0 || n == 1){
            return 1;
        }else{
            return n + factorial(n - 1);
        }
    }

   public static void main(String[] args){

       Observable<Integer> observable = Observable.range(1, 10);
       Observer<Integer> observer1 = new Observer<>() {
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
               System.out.println("Error: " + e.getMessage());
           }

           @Override
           public void onComplete() {
               System.out.println("Completed");
           }
       };

       Observer<Integer> observer2 = new Observer<>() {
           @Override
           public void onSubscribe(Disposable d) {
               System.out.println("Subscribed");
           }

           @Override
           public void onNext(Integer value) {
               System.out.println("Received: " + value + " Factorial: " + factorial(value));
           }

           @Override
           public void onError(Throwable e) {
               System.out.println("Error: " + e.getMessage());
           }

           @Override
           public void onComplete() {
               System.out.println("Completed");
           }
       };

       Observer<Integer> observer3 = new Observer<>() {
           @Override
           public void onSubscribe(Disposable d) {
               System.out.println("Subscribed");
           }

           @Override
           public void onNext(Integer value) {
               System.out.println("Received: " + value + "Sumatoria de los N valores: " + sumatoriaN(value));
           }

           @Override
           public void onError(Throwable e) {
               System.out.println("Error: " + e.getMessage());
           }

           @Override
           public void onComplete() {
               System.out.println("Completed");
           }
       };
       observable.subscribe(observer1);
       observable.subscribe(observer2);
       observable.subscribe(observer3);
       //observable.unsubscribe(observer);
   }

}

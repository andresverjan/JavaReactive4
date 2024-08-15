package org.example;
import io.reactivex.rxjava3.core.Observable;

public class Main {
    public static void main(String[] args) {
        Observable<Integer> observable = Observable.range(1, 5);
        observable.subscribe(ObserversClass.getIncrementObserver());
        observable.subscribe(ObserversClass.getFactorialObserver());
        observable.subscribe(ObserversClass.getMathObserver());
    }
}
package org.example;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class ObserversClass {
    public static Observer<Integer> getIncrementObserver() {
        return new Observer<Integer>() {
            int value = 0;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("subscribe");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                value = value + integer;
                System.out.println(value);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.err.println(e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("complete");
            }
        };
    }
        public static Observer<Integer> getFactorialObserver(){
            return new Observer<Integer>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    System.out.println("subscribe");
                }

                @Override
                public void onNext(@NonNull Integer integer) {
                    int factorial=1;
                    for(int i=1;i<=integer;i++){
                        factorial=factorial*i;
                    }
                    System.out.println(factorial);
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    System.err.println(e.getMessage());
                }

                @Override
                public void onComplete() {
                    System.out.println("complete");
                }
            };
    }
    public static Observer<Integer> getMathObserver(){
        return new Observer<Integer>() {
            int value=0;
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("subscribe");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                value = value + ((integer+integer)*integer);
                System.out.println(value);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.err.println(e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("complete");
            }
        };
    }
}

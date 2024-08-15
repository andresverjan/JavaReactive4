package com.example.Observer;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ObserverApplication {

	public static void main(String[] args) {

		Observable<Integer> observable = Observable.range(1,10);
		Observer<Integer> observer1 = new Observer<Integer>() {
			@Override
			public void onSubscribe(@NonNull Disposable d) {
				System.out.println("Subscribed");
			}

			@Override
			public void onNext(@NonNull Integer value) {
				System.out.println("Observer 1" + (value+1));
			}

			@Override
			public void onError(@NonNull Throwable e) {
				System.err.println("Observer 1 Error: " + e.getMessage());
			}

			@Override
			public void onComplete() {
				System.out.println("Observer 1 Completed");
			}
		};

		Observer<Integer> observer2 = new Observer<Integer>() {
			@Override
			public void onSubscribe(Disposable d) {
				System.out.println("Observer 2 Subscribed");
			}

			@Override
			public void onNext(Integer value) {
				System.out.println("Observer 2 (Factorial): " + factorial(value));
			}

			@Override
			public void onError(Throwable e) {
				System.err.println("Observer 2 Error: " + e.getMessage());
			}

			@Override
			public void onComplete() {
				System.out.println("Observer 2 Completed");
			}

			private int factorial(int n) {
				if (n <= 1) return 1;
				else return n * factorial(n - 1);
			}
		};

		Observer<Integer> observer3 = new Observer<Integer>() {
			@Override
			public void onSubscribe(Disposable d) {
				System.out.println("Observer 3 Subscribed");
			}

			@Override
			public void onNext(Integer value) {
				System.out.println("Observer 3 (Cuadrado): " + (value * value));
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

		observable.subscribe(observer1);
		observable.subscribe(observer2);
		observable.subscribe(observer3);

	}

}

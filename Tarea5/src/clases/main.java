package clases;

public class main {
    public static void main(String[] args) {
        MonoObserver mono = new MonoObserver();
        mono.addObserver(new Subscriber1());
        mono.addObserver(new Subscriber2());

        mono.generateNumber();
    }
}

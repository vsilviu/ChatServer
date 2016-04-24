package connections.thread.bank.account.medium;

/**
 * Created by Silviu on 14-Sep-15.
 */
public class TestSync implements Runnable {

    private int balance = 0;

    @Override
    public void run() {
        for (int i = 0; i < 50; ++i) {
            increment();
            System.out.println("Balance is " + balance);
        }
    }

    public synchronized void increment() {
        //if it is not synchronised, it will increment a previously read value!
        //2 step incrementation
        int i = balance;
        //between these steps, a thread could be interrupted, if not synced!
        balance = i + 1;
    }
}

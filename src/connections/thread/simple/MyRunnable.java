package connections.thread.simple;

/**
 * Created by Silviu on 13-Sep-15.
 */
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        try {
            go();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void go() throws InterruptedException {
        doMore();
    }

    private void doMore() throws InterruptedException {
        //Thread.sleep(2000);
        System.out.println("Top of the stack in custom thread!"); //is called second
    }
}

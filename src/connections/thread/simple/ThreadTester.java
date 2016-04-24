package connections.thread.simple;

/**
 * Created by Silviu on 13-Sep-15.
 */
public class ThreadTester {

    public static void main(String[] args) throws InterruptedException {
        Runnable threadJob = new MyRunnable();
        Thread myThread = new Thread(threadJob);
        myThread.start();
        System.out.println("Back in main!"); //is called first
    }

}

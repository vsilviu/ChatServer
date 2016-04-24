package connections.thread.simple;

/**
 * Created by Silviu on 13-Sep-15.
 */
public class MultipleThreadsOneRunner implements Runnable {

    public static void main(String[] args) {
        /* Custom threads will run the necessary run() method! */
        //Runnable myRunnable = new MyRunnable();
        MultipleThreadsOneRunner myRunnable = new MultipleThreadsOneRunner();
        Thread alpha = new Thread(myRunnable);
        Thread beta = new Thread(myRunnable);
        alpha.setName("Alpha");
        beta.setName("Beta");
        alpha.start();
        beta.start();

    }

    @Override
    public void run() {
        for(int i=0; i<60; ++i) {
            System.out.println(Thread.currentThread().getName() + " is currently running.");
        }
    }
}

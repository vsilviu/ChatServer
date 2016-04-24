package connections.thread.bank.account.medium;

/**
 * Created by Silviu on 14-Sep-15.
 */
public class TestSyncTest {

    public static void main(String[] args) {
        TestSync job = new TestSync();
        Thread a = new Thread(job);
        Thread b = new Thread(job);
        a.start();
        b.start();
    }

}

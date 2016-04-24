package connections.thread.bank.account.simple;

/**
 * Created by Silviu on 14-Sep-15.
 */
public class RyanMonicaJob implements Runnable {

    private BankAccount account = new BankAccount();

    @Override
    public void run() {
        for(int x=0 ;x<10; ++x) {
            makeWithdrawal(10);
            if (account.getDeposit() < 0) {
                System.out.println("Overdrawn!");
            }
        }
    }

    public static void main(String[] args) {
        RyanMonicaJob job = new RyanMonicaJob();
        Thread ryan = new Thread(job);
        Thread monica = new Thread(job);
        ryan.setName("Ryan");
        monica.setName("Monica");
        ryan.start();
        monica.start();
    }

    private  synchronized void makeWithdrawal(int amount) {
        if(account.getDeposit() >= amount) {
            System.out.println(Thread.currentThread().getName() + " will withdraw.");
            try{
                System.out.println(Thread.currentThread().getName() + " will sleep.");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " woke up.");
            account.withdraw(amount);
            System.out.println(Thread.currentThread().getName() + " completes withdraw.");
            System.out.println("Current deposit : " + account.getDeposit());
        } else {
            System.out.println(Thread.currentThread().getName() + " does not have any money in account :(");
        }
    }

}

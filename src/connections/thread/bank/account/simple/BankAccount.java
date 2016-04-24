package connections.thread.bank.account.simple;

/**
 * Created by Silviu on 14-Sep-15.
 */
public class BankAccount {

    public int deposit = 100;

    public int getDeposit() {
        return deposit;
    }

    public void withdraw(int amount){
        deposit -= amount;
    }

}

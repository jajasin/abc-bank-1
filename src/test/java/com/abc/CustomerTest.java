package com.abc;

import org.junit.*;
import static org.junit.Assert.assertEquals;

public class CustomerTest {
	
	@Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new CheckingAccount(0.0, Account.CHECKING);
        Account savingsAccount = new SavingsAccount(0.0, Account.SAVINGS);
		Account maxiSavingsAccount = new MaxiSavingsAccount(0.0, Account.MAXI_SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount).openAccount(maxiSavingsAccount);

        checkingAccount.deposit(100.0);
		
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
		
		maxiSavingsAccount.deposit(2000.0);
        maxiSavingsAccount.withdraw(200.0);

        checkingAccount.transfer(savingsAccount, checkingAccount, 1000.0);
        checkingAccount.transfer(maxiSavingsAccount, checkingAccount, 1000.0);

        assertEquals("Statement for Henry\n" +           
					"\n" +					   
					"Checking Account\n" +
					"  deposit $100.00\n" +
					"  deposit $1,000.00\n" +
					"  deposit $1,000.00\n" +
					"Total $2,100.00\n" +
					  "\n" +
					"Savings Account\n" +
					"  deposit $4,000.00\n" +
					"  withdrawal ($200.00)\n" +
					"  withdrawal ($1,000.00)\n" +
					"Total $2,800.00\n" +
					  "\n" +
					"Maxi Savings Account\n" +
					"  deposit $2,000.00\n" +
					"  withdrawal ($200.00)\n" +
					"  withdrawal ($1,000.00)\n" +
					"Total $800.00\n" +
					  "\n" +
					"Total In All Accounts $5,700.00\n" +
					"Number Of Accounts for Henry: 3\n" +
					"Total Interest Earned by Henry: $8.30",  henry.getStatement());
	}

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount(0.0, Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount(0.0, Account.SAVINGS));
        oscar.openAccount(new CheckingAccount(0.0, Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount(0.0, Account.SAVINGS));
        oscar.openAccount(new CheckingAccount(0.0, Account.CHECKING));
		oscar.openAccount(new MaxiSavingsAccount(0.0, Account.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}

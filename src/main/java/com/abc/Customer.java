package com.abc;

import java.util.List;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class Customer {
    private String customerName;
    private List<Account> accounts;

    public Customer(String customerName) {
        this.customerName = customerName;
        this.accounts = new ArrayList<Account>();
    }

    public String getCustomerName(){
        return customerName;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder("Statement for " + customerName + "\n");
        double total = 0.0;
        for (Account a : accounts) {
            statement.append("\n" + statementForAccount(a) + "\n");
            total += a.sumTransactions();
        }
        statement.append("\nTotal In All Accounts " + numberFormatter(total));
		statement.append("\nNumber Of Accounts for " + customerName + ": " + getNumberOfAccounts());
		statement.append("\nTotal Interest Earned by " + customerName + ": " +  numberFormatter(totalInterestEarned()));
        return statement.toString();
    }
	
	private String getAccountName(Account a) {
        String accountName = "";

        switch(a.getAccountType()){
            case Account.CHECKING:
                accountName = "Checking Account\n";
                break;
            case Account.SAVINGS:
                accountName = "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS: 
                accountName = "Maxi Savings Account\n";
                break;
        }
		return accountName;
	}

    private String statementForAccount(Account a) {
		StringBuilder accountStatement = new StringBuilder(getAccountName(a));
		
        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
			accountStatement.append( "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + numberFormatter(t.amount) + "\n");
            total += t.amount;
        }
		accountStatement.append("Total " + numberFormatter(total));
        return accountStatement.toString();
    }
	
	// Made this a class method because it only operates on the argument provided to it
	public static String numberFormatter(double number) {
		String formatterNumber = "";
		NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
		formatterNumber = defaultFormat.format(number);
		return formatterNumber;
	}
}

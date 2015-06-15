package com.abc;

import com.util;

import java.util.List;
import java.util.ArrayList;

import java.util.Random;
import static java.lang.Math.abs;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account implements IAccount {
	private double balance;	
	private int accountType;
	private long accountNumber;
	
	// Tracks our transactions
	public List<Transaction> transactions; 
	
	// Lock to protect transactions
	private static Lock lock = new ReentrantLock(); 

	// Initializes the account balance, account type, and auto generate the account number
	public Account(final double balance, final int accountType) {
		this.balance = balance;
		this.accountType = accountType;
		this.accountNumber = abs(new Random().nextLong());
		this.transactions = new ArrayList<Transaction>();
	}
	
	// Returns account balance
	@Override
	public double getBalance() {
		return balance;
	}

	// Returns account type
	@Override
    public int getAccountType(){
        return accountType;
    }
	
	// Returns account number
	@Override
    public long getAccountNumber(){
        return accountNumber;
    }
	
	// Make a deposit into the account
	@Override
	public void deposit(final double amount) {
		lock.lock();
		try {
			if (amount <= 0.0){
				throw new IllegalArgumentException("Invalid amount for deposit : " + amount);
			}else{
				balance += amount;
				transactions.add(new Transaction(amount));
			}
		} finally {
			lock.unlock();
		}	
	}
	
	// Make withdrawal from the account
	@Override
	public void withdraw(final double amount) {
		lock.lock();
		try {
			if (balance < amount) {
			   throw new IllegalArgumentException("Insufficient funds for withdrawal : " + balance);
		   }else {
		   	    balance -= amount;
				transactions.add(new Transaction(-amount));
			}
		} finally {
			lock.unlock();
		}	
	}
	
	//Transfer funds from one account to another
	@Override
    public void transfer(final Account sourceAccount, final Account targetAccount, final double amount) {
		lock.lock();
		try {
			sourceAccount.withdraw(amount);
			targetAccount.deposit(amount); 
		} finally {
			lock.unlock();
		}		 
    }  

	// Returns sum of all transactions
	@Override
	public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    // Calculate the interest rate for a given account type
	@Override
	public double interestEarned()  {
		switch(getAccountType()){
			case ConstantUtil.CHECKING: 
				return getBalance() * ConstantUtil.ONE_PERCENT_RATE; 
			case ConstantUtil.SAVINGS: 
				if (getBalance() <= 1000)
					return getBalance() * ConstantUtil.ONE_PERCENT_RATE; 
				else
					return 1 + (getBalance() - 1000) * ConstantUtil.TWO_PERCENT_RATE;  
			case ConstantUtil.MAXI_SAVINGS: 
                if (getBalance() <= 1000)
                    return getBalance() *  ConstantUtil.TWO_PERCENT_RATE; 
                if (getBalance() <= 2000)
                    return 20 + (getBalance() - 1000) * ConstantUtil.FIVE_PERCENT_RATE;
               return 70 + (getBalance() - 2000) * ConstantUtil.TEN_PERCENT_RATE;
			default: 
			   return getBalance() * ConstantUtil.ONE_PERCENT_RATE;
		}
	}

    @Override
	public boolean equals(Object obj){
		if (this == obj) return true; 
        if (!(obj instanceof Account)) return false;
        Account other = (Account) obj;
        return balance == other.getBalance() && 
			   accountType == other.getAccountType() && 
			   accountNumber == other.getAccountNumber();
    }

	@Override
    public int hashCode() {
        return (int)(31 * accountType + balance + accountNumber);
    }
    
    interface IAccount {
	public double getBalance();    
	public int getAccountType();
	public long getAccountNumber();
	public double interestEarned();
	public double sumTransactions();
	public void deposit(final double amount);
	public void withdraw(final double amount);
	public void transfer(final Account sourceAccount, final Account targetAccount, final double amount);
   }
}

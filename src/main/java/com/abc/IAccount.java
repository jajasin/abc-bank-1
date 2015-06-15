package com.abc;

public interface IAccount {
	public double getBalance();
	public int getAccountType();
	public long getAccountNumber();
	public void deposit(final double amount);
	public void withdraw(final double amount);
	public void transfer(final Account sourceAccount, final Account targetAccount, final double amount);
	public double sumTransactions();
	public double interestEarned() ;
}

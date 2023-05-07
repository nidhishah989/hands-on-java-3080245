package bank;

import bank.exception.AmountException;
import bank.exception.MinimumBalanceException;

public class Account {

  private int id;
  private String type;
  private Double balance;

  public Account(int id, String type, Double balance) {
    setId(id);
    setType(type);
    setBalance(balance);
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Double getBalance() {
    return this.balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public void deposit(Double amount) throws AmountException {
    if (amount < 1) {
      throw new AmountException("The minimum amount for deposit is 1.00$.");
    }
    balance = balance + amount;
    DataSource.updateBalance(id, balance);

  }

  public void withdraw(Double amount) throws MinimumBalanceException {
    double newbalance = balance - amount;
    if (amount.equals(balance) || newbalance <= 1) {
      throw new MinimumBalanceException("Account balance should not be less than 1.00 $");
    } else {
      balance = balance - amount;
      DataSource.updateBalance(id, balance);
    }
  }

}

package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import bank.exception.AmountException;
import bank.exception.MinimumBalanceException;

public class Menu {
  private Scanner scanner;

  private Customer userauthentication() {
    System.out.println("Enter Username: ");
    String username = scanner.next();

    System.out.println("Enter Password: ");
    String password = scanner.next();
    Customer user = null;
    // check authentication here
    try {
      user = Authenticator.login(username, password);
    } catch (LoginException e) {
      System.out.println("Error: " + e.getMessage());
    }

    return user;
  }

  private void showMenu(Customer user, Account userinfo) {
    int selection = 0;
    while (selection != 4 && user.isAuthenticate()) {
      System.out.println("=================================================");
      System.out.println("Please select one of the following options: ");
      System.out.println("1:Deposit");
      System.out.println("2:WithDraw");
      System.out.println("3:Check Balance");
      System.out.println("4:Exit");
      System.out.println("=================================================");

      selection = scanner.nextInt();
      Double amount;
      switch (selection) {
        case 1:
          System.out.println("How much would you like to deposit? ");
          amount = scanner.nextDouble();
          // deposit amount
          try {
            userinfo.deposit(amount);
            System.out.println("New Balance: " + userinfo.getBalance());
          } catch (AmountException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Try Again.");
          }
          break;
        case 2:
          System.out.println("How much would you like to withdraw?");
          amount = scanner.nextDouble();
          try {
            userinfo.withdraw(amount);
            System.out.println("New Balance: " + userinfo.getBalance());
          } catch (MinimumBalanceException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Try Again.");
          }
          break;
        case 3:
          System.out.println("Current Balance: " + userinfo.getBalance());
          break;
        case 4:
          user.setAuthenticate(false);
          System.out.println("Thank you banking. ");
          break;
        default:
          System.out.println("This is invalid option. Try Again");

      }

    }
  }

  public static void main(String[] args) {

    System.out.println("Welcome to bank system.");
    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);
    // ask for username and password
    Customer user = menu.userauthentication();
    if (user != null) {
      Account userinfo = DataSource.getaccountinfo(user.getAccountId());
      menu.showMenu(user, userinfo);
    }
    menu.scanner.close();
  }

}

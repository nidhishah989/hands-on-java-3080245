package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

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

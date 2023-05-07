package bank;

import javax.security.auth.login.LoginException;

public class Authenticator {

  // either surround exception in try and cathc or add throws exception in method
  // declaration
  public static Customer login(String username, String password) throws LoginException {

    Customer custinfo = DataSource.getCustomer(username);
    if (custinfo == null) {
      throw new LoginException("Username is invalid.");
    }

    if (password.equals(custinfo.getPassword())) {
      custinfo.setAuthenticate(true);
      return custinfo;
    }

    throw new LoginException("UnSuccess: Check username and password again.");
  }

  public static void logout(Customer customer) {
    customer.setAuthenticate(false);
  }
}

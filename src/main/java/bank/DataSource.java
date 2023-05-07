package bank;

// the link for java + database: https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {

  public static Connection connect() {

    String sql_DB = "jdbc:sqlite:resources/bank.db";

    Connection conn = null;

    try {
      conn = DriverManager.getConnection(sql_DB);
      // System.out.println("SQLite connection has established.");

    } catch (SQLException e) {
      // normal print message about exception
      System.out.println(e.getMessage());
      // used to debug which line of code in which method get exception- detailed
      // logged error information

      e.printStackTrace();
    }

    return conn;
  }

  // Get customer information by username
  public static Customer getCustomer(String username) {
    Customer customerresult = null;

    // sql statement for database query
    String sqlstm = "select * from Customers where username = ?";
    // Use try- resource method to close connection and statement and quary to avoid
    // leak-
    // https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
    try (Connection conn = connect();
        PreparedStatement stm = conn.prepareStatement(sqlstm)) { // preparestatement is used cause we have parameter to
                                                                 // add- to avoid sql injection
      stm.setString(1, username);
      // execute preparestatemet with try resources method
      try (ResultSet result = stm.executeQuery()) { // executeQuery will give one result object. ResultSet is object
                                                    // from java.sql
        while (result.next()) {// pointer to row like cursor
          customerresult = new Customer(
              result.getInt("id"),
              result.getString("name"),
              result.getString("username"),
              result.getString("password"),
              result.getInt("account_id"));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return customerresult;
  }

  // Get customer account information
  public static Account getaccountinfo(int accountid) {

    String sqlstm = "select * from Accounts where id=?";
    Account accresult = null;

    try (Connection conn = connect();
        PreparedStatement stm = conn.prepareStatement(sqlstm);) {
      stm.setInt(1, accountid);
      try (ResultSet res = stm.executeQuery()) {
        while (res.next()) {
          accresult = new Account(
              res.getInt("id"),
              res.getString("type"),
              res.getDouble("balance"));
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return accresult;
  }

  // update bank balance
  public static void updateBalance(int accountid, double newbalance) {
    String sqlstm = "update Accounts set balance=? where id=?";
    try (Connection conn = connect();
        PreparedStatement stm = conn.prepareStatement(sqlstm);) {
      stm.setDouble(1, newbalance);
      stm.setInt(2, accountid);
      stm.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
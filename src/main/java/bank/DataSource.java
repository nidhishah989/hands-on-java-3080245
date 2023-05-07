package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

  public static Connection connect() {

    String sql_DB = "jdbc:sqlite:resources/bank.db";

    Connection conn = null;

    try {
      conn = DriverManager.getConnection(sql_DB);
      System.out.println("SQLite connection has established.");

    } catch (SQLException e) {
      // normal print message about exception
      System.out.println(e.getMessage());
      // used to debug which line of code in which method get exception- detailed
      // logged error information

      e.printStackTrace();
    }

    return conn;
  }

  public static void main(String[] args) {

    connect();
  }
}
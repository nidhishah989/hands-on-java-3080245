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

  // Get customer information by username
  public static Customer getCustomer(String username){
    Customer customerresult=null;

    //sql statement for database query
    String sqlstm = "select * from Customers where username = ?";
    // Use try- resource method to close connection and statement and quary to avoid leak-
    // https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
    try(Connection conn = connect();
        PreparedStatement stm=conn.prepareStatement(sqlstm)){ //preparestatement is used cause we have parameter to add- to avoid sql injection
          stm.setString(1, username);
          //execute preparestatemet with try resources method
          try(ResultSet result=stm.executeQuery()){ //executeQuery will give one result object. ResultSet is object from java.sql
            while(result.next()) {//pointer to row like cursor
              customerresult = new Customer(
                result.getInt("id"),
                result.getString("name"),
                result.getString("username"),
                result.getString("password"),
                result.getInt("account_id")
              );
            }
          }
    }
    catch(SQLException e){
      e.printStackTrace();
    }

    return customerresult;
  }

  public static void main(String[] args) {
    Customer cust=getCustomer("ttoulchi5@ehow.com");
    System.out.println(cust.getName());
  }
}
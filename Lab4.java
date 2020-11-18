import java.util.Scanner;
import java.sql.*;

public class Lab4 {

   static final String jdbcDriver = "com.mysql.jdbc.Driver";
   static final String dbAddress = "jdbc:mysql://10.0.10.3:3306/";
   static final String userPass = "?user=root&password=password";
   static final String dbName = "db";
   static final String userName = "root";
   static final String password = "password";

   static Connection con;

   public static void main(String[] args) {
      Scanner scan = new Scanner(System.in);
      boolean run = true;
      int operation;
      
      try {
         Class.forName(jdbcDriver);
         con = DriverManager.getConnection(dbAddress + dbName, userName, password);
         Statement  statement = con.createStatement();
         int myResult = statement.executeUpdate(
            "CREATE TABLE IF NOT EXISTS `" +dbName+ "`.`persons` ( `id` INT NOT NULL AUTO_INCREMENT , `name` VARCHAR(64) NOT NULL , `surname` VARCHAR(64) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;"
         );
         statement.close();
      } catch (ClassNotFoundException | SQLException e) {
         e.printStackTrace();
      }

      scan.nextLine();
      
      while(run) {
         System.out.println("\nWitaj w menedżerze ludzi!");
         System.out.println("1. Dodaj nową osobę");
         System.out.println("2. Usuń osobę");
         System.out.println("3. Wyświetl listę");
         System.out.println("4. Zakończ pracę");
         System.out.print("Wybierz operacje: ");
         operation = scan.nextInt();
         scan.nextLine();

         switch(operation) {
            case 1: {
               System.out.println("Dodawanie nowej osoby");
               System.out.print("Imie: ");
               String name = scan.nextLine();
               System.out.print("Nazwisko: ");
               String surname = scan.nextLine();

               try {
                  PreparedStatement prpStmt = con.prepareStatement("INSERT INTO persons ( name, surname) VALUES (?, ?)");
                    
                  prpStmt.setString(1, name);
                  prpStmt.setString(2, surname);
                    
                  prpStmt.execute();
                  System.out.println("Dodano nową osobę!");
               } catch (SQLException e) {
                  e.printStackTrace();
               }
               break;
            }
            case 2: {
               System.out.println("Podaj id osoby");
               System.out.print("Id osoby: ");
               int id = scan.nextInt();

               try {
                  PreparedStatement prpStmt = con.prepareStatement("DELETE FROM persons WHERE ID = ?");
                  
                  prpStmt.setInt(1, id);
                  
                  prpStmt.execute();
                  System.out.println("Usunięto osobę");
               } catch (SQLException e) {
                  e.printStackTrace();
               }
               break;
            }
            case 3: {
               try {
                  System.out.println("Lista osób");
                  Statement  statement = con.createStatement();
                  ResultSet rs =  statement.executeQuery("SELECT * FROM persons");
                  while(rs.next()) {
                     int id = rs.getInt("id");
                     String name = rs.getString("name");
                     String surname = rs.getString("surname");

                     System.out.println(id + " | " + name + " | " + surname);
                  }
                  statement.close();
               } catch (SQLException e) {
                  e.printStackTrace();
               }
               break;
            }
            case 4: {
               System.out.println("Zamykanie aplikacji");
               run = false;
               break;
            }
         }
      }
   }
}

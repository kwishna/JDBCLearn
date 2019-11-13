package DataBase;

import java.sql.*;

public class DataBase5 {

    public static void main(String[] args) {
        //            PreparedStatement s = c.prepareStatement("CREATE TABLE STUDENT(  StudentId INT PRIMARY KEY NOT NULL," +
        //                    "StudentName TEXT NOT NULL," +
        //                    "Roll_No INT NOT NULL UNIQUE," +
        //                    "Fees REAL," +
        //                    "Address CHAR(50) )")
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + System.getProperty("user.dir") + "\\Resources\\sqliteDB\\krishna1.db")) {
            try (PreparedStatement s = c.prepareStatement("INSERT INTO student (StudentId, StudentName, Roll_No, Fees, Address) values (?, ?, ?, ?, ?)")) {
                s.setObject(1, 2);
                s.setObject(2, "Krishna Kumar Singh");
                s.setObject(3, 2);
                s.setObject(4, 1000);
                s.setObject(5, "India Gate, US");

                var i = s.executeUpdate();
                c.commit();
                System.out.println(i);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}

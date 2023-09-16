import java.sql.*;

public class ConnectionProvider {
    public static Statement getConnection() {
        Statement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/swaad", "root", "Chhavisql30.");
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stmt;
    }
}
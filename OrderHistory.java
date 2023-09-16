import javax.swing.*;
import java.sql.*;

public class OrderHistory {
    JFrame frame;
    JScrollPane sp;
    JTable jt;
    String userName;

    public OrderHistory(String s) {
        userName = s;
    }

    void launch() {
        String[][] data = new String[10][7];
        String[] columns = {"Order_no", "Date", "Time", "Method", "Amount", "ETA", "email"};
        frame = new JFrame("Order History");
        try {
            Statement stmt = ConnectionProvider.getConnection();
            ResultSet rs = stmt.executeQuery("select * from orders where email = '"+userName+"';");
            int i = 0;
            while (rs.next()) {
                data[i][0] = Integer.toString(rs.getInt(1));
                data[i][1] = rs.getString(2);
                data[i][2] = rs.getString(3);
                data[i][3] = rs.getString(4);
                data[i][4] = Integer.toString(rs.getInt(5));
                data[i][5] = Integer.toString(rs.getInt(6));
                data[i][6] = rs.getString(7);
                i++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        jt = new JTable(data, columns);
        System.out.println(data[1][0]);
        jt.setBounds(0, 0, 1000, 400);

        sp = new JScrollPane(jt);

        frame.add(sp);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}

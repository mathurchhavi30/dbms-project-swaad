
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.time.*;

public class Order {
    
    private JFrame frame;
    private JLabel l1, l2;
    private JTextField t1;
    private JComboBox<String> c1;
    private JButton b1, b2, b3;
    private static int MAX_NO_OF_ITEMS = 5;
    private String currentUser;
    private class OrderItem {
        String item;
        int quantity;
    }
    OrderItem[] items = new OrderItem[MAX_NO_OF_ITEMS];
    private static int i; // Counter for above array

    public Order(String currentUser) {
        this.currentUser = currentUser;
        i = 0;
    }

    public void launch() {
        initComponents();
        
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(i > 4) {
                    JOptionPane.showMessageDialog(frame, "Can't add more items");
                    return;
                }
                String s = (String)c1.getSelectedItem();
                int quantity = Integer.parseInt(t1.getText());
                items[i] = new OrderItem();
                items[i].item = s;
                items[i].quantity = quantity;
                i++;
            }
        });

        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Statement stmt = ConnectionProvider.getConnection();
                    int[] food = new int[MAX_NO_OF_ITEMS];
                    int j = 0; // Counter for items[]
                    int price = 0;
                    ResultSet rs = null;
                        while (items[j] != null) {
                            rs = stmt.executeQuery("select food_id, price from food where name = '"+items[j].item+"';");
                            if (rs.next()) {
                                food[j] = rs.getInt(1);
                                price += rs.getInt(2) * items[j].quantity;
                                j++;
                            }
                        }
                    String date = LocalDate.now().toString();
                    String time = LocalTime.now().toString().substring(0,8);
                    int eta = (int)(Math.random() * 60);
                    String method = null;
                    if (Math.random() > 0.5) {
                        method = "cod";
                    }
                    else {
                        method = "card";
                    }
                    int orderNo = (int) (Math.random() * 10000);
                    stmt.executeUpdate("insert into orders values('"+orderNo+"','"+date+"','"+time+"','"+method+"','"+price+"','"+eta+"','"+currentUser+"');");
                    j = 0;
                    while (items[j] != null) {
                        stmt.executeUpdate("insert into items values('"+food[j]+"','"+orderNo+"','"+items[j].quantity+"');");
                        j++;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new OrderHistory(currentUser).launch();
            }
        });        
    }

    private String[] getItems() {
        String[] items = new String[MAX_NO_OF_ITEMS];
        try {
            Statement stmt = ConnectionProvider.getConnection();
            ResultSet rs = stmt.executeQuery("Select name from food;");
            int i = 0;
            while(rs.next()) {
                items[i] = rs.getString(1);
                i++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return items;
    }
    
    private void initComponents() {
        frame = new JFrame("Order");

        String items[] = getItems();
        c1 = new JComboBox<String>(items);
        c1.setBounds(150, 120, 150, 50);

        l1 = new JLabel("Quantity");
        l1.setBounds(50, 50, 100, 30);
        l2 = new JLabel("Food Item");
        l2.setBounds(50, 120, 100, 30);

        t1 = new JTextField();
        t1.setBounds(150, 50, 160, 30);

        b1 = new JButton("Add");
        b1.setBounds(150, 350, 90, 30);
        b2 = new JButton("Order");
        b2.setBounds(300, 350, 90, 30);
        b3 = new JButton("Show Order History");
        b3.setBounds(200, 400, 200, 30);

        frame.add(l1);
        frame.add(t1);
        frame.add(l2);
        frame.add(b1);
        frame.add(b2);
        frame.add(b3);
        frame.add(c1);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}

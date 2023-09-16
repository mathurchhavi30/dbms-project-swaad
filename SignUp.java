import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class SignUp {
    private JFrame frame;
    private JLabel l1, l2, l3, l4, l5;
    private JTextField t1, t2, t3, t4, t5;
    private JButton b1, b2;

    public void launch() {
        initComponents();        
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Statement stmt = ConnectionProvider.getConnection();
                    stmt.executeUpdate("insert into customer values('"+t1.getText()+"','"+t2.getText()+"','"+t3.getText()+"','"+t4.getText()+"','"+t5.getText()+"');");
                    t1.setText("");
                    t2.setText("");
                    t3.setText("");
                    t4.setText("");
                    t5.setText("");
                    JOptionPane.showMessageDialog(frame, "Signed up successfully");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Login().launch();
            }
        });
    }
    private void initComponents() {
        frame = new JFrame("Sign Up");

        l1 = new JLabel("Email ID:");
        l1.setBounds(50, 50, 100, 30);
        l2 = new JLabel("First Name:");
        l2.setBounds(50, 120, 100, 30);
        l3 = new JLabel("Last Name:");
        l3.setBounds(410, 120, 100, 30);
        l4 = new JLabel("Address:");
        l4.setBounds(370, 50, 100, 30);
        l5 = new JLabel("Password:");
        l5.setBounds(370, 190, 100, 30);

        t1 = new JTextField();
        t1.setBounds(150, 50, 160, 30);
        t2 = new JTextField();
        t2.setBounds(150, 120, 130, 30);
        t3 = new JTextField();
        t3.setBounds(490, 120, 130, 30);
        t4 = new JTextField();
        t4.setBounds(490, 50, 160, 30);
        t5 = new JTextField();
        t5.setBounds(490, 190, 130, 30);

        b1 = new JButton("SignUp");
        b1.setBounds(400, 300, 90, 30);
        b2 = new JButton("Back to Login");
        b2.setBounds(600,300,200,30);

        frame.add(l1);
        frame.add(t1);
        frame.add(l2);
        frame.add(t2);
        frame.add(l3);
        frame.add(t3);
        frame.add(l4);
        frame.add(t4);
        frame.add(b1);
        frame.add(b2);
        frame.add(l5);
        frame.add(t5);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}

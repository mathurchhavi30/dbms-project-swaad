import javax.swing.*;
import java.sql.*;
import java.awt.event.*;

public class Login {

    private JFrame f;
    private JButton bLogin;
    private JButton bSignup;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JLabel lUsername;
    private JLabel lPassword;

    public void launch() {
        initComponents();

        bSignup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new SignUp().launch();
            }
        });

        bLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            try {
                Statement stmt = ConnectionProvider.getConnection();
                String email = tfUsername.getText();
                String password = new String(pfPassword.getPassword());
                ResultSet rs = stmt.executeQuery("select password from customer where email = '"+email+"';");
                if (rs.next() && rs.getString(1).equals(password)) {
                    tfUsername.setText("");
                    pfPassword.setText("");
                    JOptionPane.showMessageDialog(f,"Login successful");
                    f.dispose();
                    new Order(email).launch();
                }
                else {
                    tfUsername.setText("");
                    pfPassword.setText("");
                    JOptionPane.showMessageDialog(f,"Incorrect credentials");
                }
            }
            catch (Exception ex) {
                System.out.println(ex);
                JOptionPane.showMessageDialog(f, "Fail");
            }
            }
        });
    }

    private void initComponents() {
        f = new JFrame("LOGIN");
        lUsername = new JLabel("Username");
        lUsername.setBounds(50, 50, 100, 100);
        lPassword = new JLabel("Password");
        lPassword.setBounds(50, 200, 100, 100);
        tfUsername = new JTextField();
        tfUsername.setBounds(200, 50, 250, 100);
        pfPassword = new JPasswordField();
        pfPassword.setBounds(200, 200, 250, 100);
        bLogin = new JButton("Login");
        bLogin.setBounds(100, 350, 125, 100);
        bSignup = new JButton("Sign Up");
        bSignup.setBounds(275, 350, 125, 100);
        f.getContentPane().add(lUsername);
        f.getContentPane().add(lPassword);
        f.getContentPane().add(tfUsername);
        f.getContentPane().add(pfPassword);
        f.getContentPane().add(bLogin);
        f.getContentPane().add(bSignup);
        f.setSize(500, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
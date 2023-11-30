import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class LoginScreen {
    private final JFrame frame;
    private JTextField emailField;
    private JPasswordField passwordField;
    private static final String URL = "jdbc:mysql://localhost:3306/NaoConformidadeUPX";
    private static final String usuario = "root";
    private static final String senha_banco = "MMatheus2204@!";

    public LoginScreen() {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(8, 2));

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Senha:");
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Entrar");
        JButton forgotPasswordButton = new JButton("Esqueci a Senha");

        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(loginButton);
        frame.add(forgotPasswordButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String nomeUsuario = authenticateUser(email, password);
                if (nomeUsuario != null) {
                    JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
                    MainScreen mainScreen = new MainScreen(nomeUsuario);
                    mainScreen.setVisible(true);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Login falhou. Verifique suas credenciais.");
                }
            }
        });

        forgotPasswordButton.addActionListener((ActionEvent e) -> {
            ForgetPassword forgetPassword = new ForgetPassword(LoginScreen.this);
            forgetPassword.setVisible(true);
            
            frame.dispose();
        });

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    String authenticateUser(String email, String senha) {
        String query = "SELECT nome FROM usuarios WHERE email = ? AND senha = ?";
        
        try (Connection connection = DriverManager.getConnection(URL, usuario, senha_banco);
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()){
                return resultSet.getString("nome");
            } else {
                return null;
            }
        } catch (SQLException e) {
// Trate qualquer exceção que possa ocorrer durante a execução da consulta.
            return null;
        }
    }
    
    boolean resetPassword(String email, String data) {
        String query = "SELECT * FROM usuarios WHERE email = ? AND data_nasc = ?";
        
        try (Connection connection = DriverManager.getConnection(URL, usuario, senha_banco);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, data);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            return resultSet.next(); // Retorna true se encontrar um registro correspondente, senão false.
        } catch (SQLException e) {
// Trate qualquer exceção que possa ocorrer durante a execução da consulta.
                        return false; // Em caso de erro, considere a autenticação como falha.
        }
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            LoginScreen main = new LoginScreen();
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC do SQLite não encontrado.");
    }}
     
     public static void updatePassword(String email, String newPassword) {
        String query = "UPDATE usuarios SET senha = ? WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(URL, usuario, senha_banco);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar a senha: " + e.getMessage());
        }
    }

    void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
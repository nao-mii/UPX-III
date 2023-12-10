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
        //cria o frame de layout
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

        //cria funcionalidade do botao de login
        loginButton.addActionListener((ActionEvent e) -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String nomeUsuario = authenticateUser(email, password);
            if (nomeUsuario != null) {
                JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
            } else {
                JOptionPane.showMessageDialog(null, "Login falhou. Verifique suas credenciais.");
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

    //autentica o usuario se conectando com o bd
    String authenticateUser(String email, String senha) {
        String query = "SELECT nome, tipo FROM usuarios WHERE email = ? AND senha = ?";
        
        try (Connection connection = DriverManager.getConnection(URL, usuario, senha_banco);
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()){
                String nomeUsuario = resultSet.getString("nome");
                String tipoUsuario = resultSet.getString("tipo");
                
                if("usuario".equals(tipoUsuario)){
                    openMainScreen(nomeUsuario);
                }else if("master".equals(tipoUsuario)){
                    openMasterScreen(nomeUsuario);
                }else {
                    return null;
                }
                return nomeUsuario;
             
            } else {
                return null;
            }
        } catch (SQLException e) {
// Trate qualquer exceção que possa ocorrer durante a execução da consulta.
            return null;
        }
    }
    //dependendo do login utilizado, redirecionada para a tela MainScreen
    private void openMainScreen(String nomeUsuario){
        JOptionPane.showMessageDialog(null,"Login bem-sucedido como usuário!");
        MainScreen mainScreen = new MainScreen(nomeUsuario);
        mainScreen.setVisible(true);
        frame.dispose();
    }

    //dependendo do login utilizado, redirecionada para a tela MasterScreen
    private void openMasterScreen(String nomeUsuario){
        JOptionPane.showMessageDialog(null, "Login bem-sucedido como master");
        MasterScreen masterScreen = new MasterScreen(nomeUsuario);
        masterScreen.setVisible(true);
        frame.dispose();
    }
    
    //caso a senha precise ser resetada
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
     
    //atualiza a senha no bd casa tenha sido resetada
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

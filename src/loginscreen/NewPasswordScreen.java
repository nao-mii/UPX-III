import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NewPasswordScreen extends JFrame {
    private JTextField passwordField;
    private JTextField confirmPasswordField;

    public NewPasswordScreen(LoginScreen main, String email) {
        setTitle("Redefinir Senha");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLayout(new GridLayout(4, 2));

        JLabel newPasswordLabel = new JLabel("Nova Senha:");
        passwordField = new JPasswordField();
        JLabel confirmPasswordLabel = new JLabel("Confirme a Nova Senha:");
        confirmPasswordField = new JPasswordField();

        JButton resetPasswordButton = new JButton("Redefinir Senha");

        add(newPasswordLabel);
        add(passwordField);
        add(confirmPasswordLabel);
        add(confirmPasswordField);
        add(resetPasswordButton);

        resetPasswordButton.addActionListener((ActionEvent e) -> {
            String newPassword = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            
            if (newPassword.equals(confirmPassword)) {
                LoginScreen.updatePassword(email, newPassword);
                JOptionPane.showMessageDialog(null, "Senha redefinida com sucesso!");
                
                dispose(); // Fecha a tela de redefinição de senha
                MainScreen loginScreen = new MainScreen("nome do usuario");
                loginScreen.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "As senhas não coincidem. Tente novamente.");
            }
        });
        
        JButton backButton = new JButton("Voltar para o Login.");
        backButton.addActionListener((ActionEvent e) -> {
            dispose();
            
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });
        
        add(backButton);
    }

}

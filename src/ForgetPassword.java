import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ForgetPassword {
    private final JFrame frame;
    private JTextField emailField;
    private JTextField dataField;
    private LoginScreen main;
    
    public ForgetPassword(LoginScreen main){
        //cria o frame de layout
        frame = new JFrame("Redefinir Senha");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,200);
        frame.setLayout(new GridLayout(2,2));
        
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel dataLabel = new JLabel("Data de Nascimento (dd/mm/yyyy):");
        dataField = new JTextField();
        
        JButton resetButton = new JButton("Redefinir Senha");
        
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(dataLabel);
        frame.add(dataField);
        frame.add(resetButton);
        
        //cria a funcionaldade do botao de reset que redireciona para a tela de criação de senha
        resetButton.addActionListener((ActionEvent e) -> {
            String email = emailField.getText();
            String data = dataField.getText();
            if (main.resetPassword(email, data)){
                NewPasswordScreen newPasswordScreen = new NewPasswordScreen(main, email);
                newPasswordScreen.setVisible(true);
                
                frame.dispose();
            }else{
                JOptionPane.showMessageDialog(null, "E-mail ou Data de Nascimento incorretos.");
            }
        });
        
        frame.setVisible(true);
        
    }

    void setVisible(boolean b) {
        frame.setVisible(b);
    }
}
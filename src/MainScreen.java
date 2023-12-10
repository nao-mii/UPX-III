import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainScreen extends JFrame {
    private String nomeUsuario;
    
    public MainScreen(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
        
        setTitle("Sistema de Não Conformidade");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLayout(new BorderLayout());
        
        getContentPane().setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        JLabel welcomeLabel = new JLabel("Bem-vindo ao Sistema de Não Conformidade!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        headerPanel.add(welcomeLabel);
        
        // Botões para tipos de não conformidades
        JButton energiaButton = new JButton("Energia");
        JButton aguaButton = new JButton("Água");
        JButton residuosButton = new JButton("Descarte de Resíduos");
        JButton acessibilidadeButton = new JButton("Acessibilidade");
        JButton manutencaoButton = new JButton("Manutenção");
        JButton areasVerdesButton = new JButton("Áreas Verdes");
        JButton segurancaButton = new JButton("Segurança");
        
        JButton perfilButton = new JButton ("Perfil");

        // Adicionando ActionListeners para os botões
        energiaButton.addActionListener((ActionEvent e) -> {
            EnergiaForm energiaForm = new EnergiaForm(nomeUsuario);
            energiaForm.setVisible(true);
        });
        
        aguaButton.addActionListener((ActionEvent e) -> {
            AguaForm aguaForm = new AguaForm(nomeUsuario);
            aguaForm.setVisible(true);
        });
        
        residuosButton.addActionListener((ActionEvent e) -> {
            DescarteForm descarteForm = new DescarteForm(nomeUsuario);
            descarteForm.setVisible(true);
        });
        
        acessibilidadeButton.addActionListener((ActionEvent e) -> {
            AcessForm acessForm = new AcessForm(nomeUsuario);
            acessForm.setVisible(true);
        });
        
        manutencaoButton.addActionListener((ActionEvent e) -> {
            ManuForm manuForm = new ManuForm(nomeUsuario);
            manuForm.setVisible(true);
        });
        
        areasVerdesButton.addActionListener((ActionEvent e) -> {
            AreasForm areasForm = new AreasForm(nomeUsuario);
            areasForm.setVisible(true);
        });
        
        segurancaButton.addActionListener((ActionEvent e) -> {
            SegurancaForm segurancaForm = new SegurancaForm(nomeUsuario);
            segurancaForm.setVisible(true);
        });
        
        perfilButton.addActionListener((ActionEvent e) -> {
            UserProfile userProfile = new UserProfile(nomeUsuario, MainScreen.this);
            dispose();
            userProfile.setVisible(true);

            EnergiaForm energiaForm = new EnergiaForm(nomeUsuario);
            energiaForm.setVisible(false);
            
            AguaForm aguaForm = new AguaForm(nomeUsuario);
            aguaForm.setVisible(false);
            
            AreasForm areasForm = new AreasForm(nomeUsuario);
            areasForm.setVisible(false);
            
            DescarteForm descarteForm = new DescarteForm(nomeUsuario);
            descarteForm.setVisible(false);
            
            ManuForm manuForm = new ManuForm(nomeUsuario);
            manuForm.setVisible(false);
            
            SegurancaForm segurancaForm = new SegurancaForm(nomeUsuario);
            segurancaForm.setVisible(false);
        });

        // Layouts para as linhas
        JPanel firstRow = new JPanel(new FlowLayout());
        JPanel secondRow = new JPanel(new FlowLayout());
        JPanel thirdRow = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Adicionando os botões às linhas apropriadas
        firstRow.add(energiaButton);
        firstRow.add(aguaButton);
        firstRow.add(residuosButton);
        secondRow.add(acessibilidadeButton);
        secondRow.add(manutencaoButton);
        secondRow.add(areasVerdesButton);
        secondRow.add(segurancaButton);
        thirdRow.add(perfilButton);

        // Adicionando as linhas ao painel principal usando BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(firstRow, BorderLayout.NORTH);
        mainPanel.add(secondRow, BorderLayout.CENTER);
        mainPanel.add(thirdRow, BorderLayout.SOUTH);
        
        // Adicionando o rótulo de boas-vindas no topo
        add(welcomeLabel, BorderLayout.NORTH);

        // Adicionando o painel principal ao centro do JFrame
        add(mainPanel, BorderLayout.CENTER);
        
        setVisible(true);
        setLocationRelativeTo(null);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainScreen mainScreen = new MainScreen("nome do usuario");
            mainScreen.setVisible(true);
        });
    }
}


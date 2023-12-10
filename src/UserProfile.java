import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserProfile extends JFrame {
    private final String nomeUsuario;
    public final MainScreen mainScreen;
    private JTable ticketTable;

    public UserProfile(String nomeUsuario, MainScreen mainScreen){
        this.nomeUsuario = nomeUsuario;
        this.mainScreen = mainScreen;
        setTitle("Perfil do Usuário");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel nameLabel = new JLabel("Nome do Usuário: " + nomeUsuario);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Tipo de Ocorrência");
        tableModel.addColumn("Título");
        tableModel.addColumn("Descrição");
        tableModel.addColumn("Status");
        tableModel.addColumn("Data de Abertura");

        ticketTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(ticketTable);

        JButton refreshButton = new JButton("Atualizar Tickets");
        refreshButton.addActionListener(e -> updateTicketList(tableModel));

        JButton backButton = new JButton("Voltar à Página Inicial");
        backButton.addActionListener((ActionEvent e) -> {
            mainScreen.setVisible(true);  // Torna a MainScreen visível novamente
            dispose();  // Fecha a UserProfile
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        updateTicketList(tableModel); // Atualiza a lista de tickets ao iniciar a tela

        setVisible(true);
    }

    private void updateTicketList(DefaultTableModel tableModel) {
        // Conectar ao banco de dados e buscar tickets do usuário
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/NaoConformidadeUPX", "root", "MMatheus2204@!")) {
            String query = "SELECT id, tipo_ocorrencia, titulo, descricao, status_call, data_ticket FROM tickets WHERE usuario = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nomeUsuario);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    tableModel.setRowCount(0); // Limpa a tabela antes de adicionar os novos tickets

                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String tipoOcorrencia = resultSet.getString("tipo_ocorrencia");
                        String titulo = resultSet.getString("titulo");
                        String descricao = resultSet.getString("descricao");
                        String status = resultSet.getString("status_call");
                        String data = resultSet.getString("data_ticket");

                        // Adiciona o ticket à tabela
                        tableModel.addRow(new Object[]{id, tipoOcorrencia, titulo, descricao, status, data});
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar tickets: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainScreen mainScreen = new MainScreen();
            mainScreen.setVisible(true);

            UserProfile userProfile = new UserProfile("NomeDoUsuario", mainScreen);
            userProfile.setVisible(true);
        });
    }
}

class MainScreen extends JFrame {
    public MainScreen() {
        setTitle("Main Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JButton openUserProfileButton = new JButton("Abrir UserProfile");
        openUserProfileButton.addActionListener((ActionEvent e) -> {
            UserProfile userProfile = new UserProfile("NomeDoUsuario", MainScreen.this);
            userProfile.setVisible(true);
            setVisible(false);  // Esconde a MainScreen ao abrir a UserProfile
        });

        JPanel panel = new JPanel();
        panel.add(openUserProfileButton);

        add(panel);
    }
}


import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UserProfile extends JFrame {
    private final String nomeUsuario;

    public UserProfile(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
        setTitle("Perfil do Usuário");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel nameLabel = new JLabel("Nome do Usuário: " + nomeUsuario);

        DefaultListModel<String> ticketListModel = new DefaultListModel<>();
        JList<String> ticketList = new JList<>(ticketListModel);
        JScrollPane scrollPane = new JScrollPane(ticketList);

        JButton refreshButton = new JButton("Atualizar Tickets");
        refreshButton.addActionListener(e -> updateTicketList(ticketListModel));

        panel.add(nameLabel);
        panel.add(scrollPane);
        panel.add(refreshButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        updateTicketList(ticketListModel); // Atualiza a lista de tickets ao iniciar a tela

        setVisible(true);
    }

    private void updateTicketList(DefaultListModel<String> ticketListModel) {
        // Conectar ao banco de dados e buscar tickets do usuário
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/NaoConformidadeUPX", "root", "MMatheus2204@!")) {
            String query = "SELECT id, tipo_ocorrencia, titulo, descricao, status_call FROM tickets WHERE nome = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nomeUsuario);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    ticketListModel.clear(); // Limpa a lista antes de adicionar os novos tickets

                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String tipoOcorrencia = resultSet.getString("tipo_ocorrencia");
                        String titulo = resultSet.getString("titulo");
                        String descricao = resultSet.getString("descricao");
                        String status = resultSet.getString("status_call");

                        // Adiciona o ticket à lista
                        ticketListModel.addElement("Ticket " + id + ": " + tipoOcorrencia + " - " + titulo + " - " + descricao + " - " + status);
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar tickets: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserProfile("NomeDoUsuario")); // Substitua "NomeDoUsuario" pelo nome real do usuário
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

public class MasterScreen extends JFrame {
    private final String nomeUsuario;
    private List<Ticket> openTickets;
    private List<Ticket> inProgressTickets;

    public MasterScreen(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;

        setTitle("Master Screen");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        JPanel panel = new JPanel(new BorderLayout());

        // Criar um modelo para a tabela
        TicketTableModel tableModel = new TicketTableModel(getOpenTickets());

        // Criar a tabela com base no modelo
        JTable table = new JTable(tableModel);

        // Adicionar a tabela a um JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        
        openTickets = getTicketsByStatus("Em Aberto");
        inProgressTickets = getTicketsByStatus("Em Andamento");

        // Adicionar o JScrollPane ao painel
        panel.add(scrollPane, BorderLayout.CENTER);

        // Adicionar um botão para atualizar o status
        // Alteração no código do botão atualizarStatusButton
        JButton atualizarStatusButton = new JButton("Atualizar Status");
        atualizarStatusButton.addActionListener((ActionEvent e) -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow != -1) {
                int ticketId = (int) tableModel.getValueAt(selectedRow, 0);

                String[] options = {"Em Andamento", "Fechado"};
                String newStatus = (String) JOptionPane.showInputDialog(
                        this,
                        "Selecione o novo status:",
                        "Atualizar Status",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (newStatus != null) {
                    updateTicketStatus(ticketId, newStatus);

                    // Atualizar o modelo da tabela com todos os chamados
                    tableModel.updateData(getAllTickets());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um chamado para atualizar o status.");
            }
        });

        // Adicionar o botão ao painel
        panel.add(atualizarStatusButton, BorderLayout.SOUTH);

        // Adicionar o painel ao frame
        add(panel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private List<Ticket> getOpenTickets() {
        openTickets = getAllTickets();
        return openTickets;
    }
    
    private List<Ticket> getAllTickets() {
    List<Ticket> tickets = new ArrayList<>();

    // Conectar ao banco de dados e recuperar todos os chamados
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/NaoConformidadeUPX", "root", "MMatheus2204@!");
         Statement statement = connection.createStatement()) {

        String query = "SELECT * FROM tickets";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String tipoOcorrencia = resultSet.getString("tipo_ocorrencia");
            String titulo = resultSet.getString("titulo");
            String descricao = resultSet.getString("descricao");
            String statusCall = resultSet.getString("status_call");
            String usuario = resultSet.getString("usuario");
            String tipo = resultSet.getString("tipo");
            String data_ticket = resultSet.getString("data_ticket");

            Ticket ticket = new Ticket(id, tipoOcorrencia, titulo, descricao, statusCall, usuario, tipo, data_ticket);
            tickets.add(ticket);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return tickets;
}
    
    private List<Ticket> getTicketsByStatus(String status) {
        List<Ticket> tickets = new ArrayList<>();

        // Conectar ao banco de dados e recuperar os chamados pelo status
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/NaoConformidadeUPX", "root", "MMatheus2204@!");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tickets WHERE status_call = ?")) {

            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String tipoOcorrencia = resultSet.getString("tipo_ocorrencia");
                String titulo = resultSet.getString("titulo");
                String descricao = resultSet.getString("descricao");
                String statusCall = resultSet.getString("status_call");
                String usuario = resultSet.getString("usuario");
                String tipo = resultSet.getString("tipo");
                String data_ticket = resultSet.getString("data_ticket");

                Ticket ticket = new Ticket(id, tipoOcorrencia, titulo, descricao, statusCall, usuario, tipo, data_ticket);
                tickets.add(ticket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }

     private void updateTicketStatus(int ticketId, String newStatus) {
    // Atualizar o status do chamado no banco de dados
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/NaoConformidadeUPX", "root", "MMatheus2204@!");
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tickets SET status_call = ? WHERE id = ?")) {

            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, ticketId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Classe para representar um chamado
    private static class Ticket {
        int id;
        String tipoOcorrencia;
        String titulo;
        String descricao;
        String statusCall;
        String usuario;
        String tipo;
        String data_ticket;

        public Ticket(int id, String tipoOcorrencia, String titulo, String descricao, String statusCall, String usuario, String tipo, String data_ticket) {
            this.id = id;
            this.tipoOcorrencia = tipoOcorrencia;
            this.titulo = titulo;
            this.descricao = descricao;
            this.statusCall = statusCall;
            this.usuario = usuario;
            this.tipo = tipo;
            this.data_ticket = data_ticket;
        }
    }

    // Classe para criar um modelo para a tabela
    private static class TicketTableModel extends AbstractTableModel {
        private final List<Ticket> tickets;
        private final String[] columnNames = {"ID", "Tipo de Ocorrência", "Título", "Descrição", "Status", "Usuário", "Tipo", "Data"};

        public TicketTableModel(List<Ticket> tickets) {
            this.tickets = tickets;
        }

        @Override
        public int getRowCount() {
            return tickets.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Ticket ticket = tickets.get(rowIndex);

            return switch (columnIndex) {
                case 0 -> ticket.id;
                case 1 -> ticket.tipoOcorrencia;
                case 2 -> ticket.titulo;
                case 3 -> ticket.descricao;
                case 4 -> ticket.statusCall;
                case 5 -> ticket.usuario;
                case 6 -> ticket.tipo;
                case 7 -> ticket.data_ticket;
                default -> null;
            };
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        // Método para atualizar os dados da tabela
        public void updateData(List<Ticket> newTickets) {
            if (newTickets != null) {
                tickets.clear();
                tickets.addAll(newTickets);
                fireTableDataChanged();
            }
        }

        @Override
        public void fireTableDataChanged() {
            fireTableChanged(new TableModelEvent(this));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MasterScreen("Nome de Usuário"));
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AreasForm extends JFrame {
    private JTextField tituloField;
    private JTextArea descricaoArea;
    private final JComboBox<String> statusComboBox;
    private JComboBox<String> tipoComboBox;
    private String nomeUsuario;
    
    public AreasForm(String nomeUsuario) {
        //conecta com o bd
        String URL = "jdbc:mysql://localhost:3306/NaoConformidadeUPX";
        String usuario = "root";
        String senha_banco = "MMatheus2204@!";
        
        //cria o frame de layout
        setTitle("Formulário de Areas Verdes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1500, 800);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(20, 3));

        JLabel tipoLabel = new JLabel("Tipo de Ocorrência:");
        String[] tipos = {"","Ausencia de Areas Verdes", "Sugestoes"}; // Opções para o JComboBox
        tipoComboBox = new JComboBox<>(tipos);
        
        JLabel tempoRespostaLabel = new JLabel("Tempo Médio de Resposta: 30-45 dias");

        JLabel tituloLabel = new JLabel("Título:");
        tituloField = new JTextField();
        
        JLabel descricaoLabel = new JLabel("Descrição:");
        descricaoArea = new JTextArea();
        JScrollPane descricaoScrollPane = new JScrollPane(descricaoArea);
        
        JLabel statusLabel = new JLabel("Status:");
        String[] status = {"Em Aberto", "Em Andamento", "Fechado"}; // Opções para o JComboBox de status
        statusComboBox = new JComboBox<>(status);

        panel.add(tipoLabel);
        panel.add(tipoComboBox);
        panel.add(tituloLabel);
        panel.add(tituloField);
        panel.add(descricaoLabel);
        panel.add(descricaoScrollPane);
        panel.add(statusLabel);
        panel.add(statusComboBox);
        panel.add(tempoRespostaLabel);
        

        JPanel buttonPanel = new JPanel();
        JButton salvarButton = new JButton("Salvar");
        
        //funcionalidade do botao de salvamento
        salvarButton.addActionListener((ActionEvent e) -> {
            String tipoOcorrencia = (String) tipoComboBox.getSelectedItem();
            String titulo = tituloField.getText();
            String descricao = descricaoArea.getText();
            Object selectedStatus = (String) statusComboBox.getSelectedItem();;
            
            // Conectar ao banco de dados e inserir as informações
            try (final Connection connection = DriverManager.getConnection(URL, usuario, senha_banco)) {
                String insertQuery = "INSERT INTO tickets (tipo_ocorrencia, titulo, descricao, status_call, usuario, tipo) VALUES (?, ?, ?, ?, ?, ?)";
                try (final PreparedStatement preparedStatement = connection.prepareStatement(insertQuery,Statement.RETURN_GENERATED_KEYS )) {
                    preparedStatement.setString(1, tipoOcorrencia);
                    preparedStatement.setString(2, titulo);
                    preparedStatement.setString(3, descricao);
                    preparedStatement.setString(4, (String) selectedStatus);
                    preparedStatement.setString(5, nomeUsuario);
                    preparedStatement.setString(6, "Areas Verdes");
                    preparedStatement.execute();
                    
                    // Obter o ID do ticket inserido
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        JOptionPane.showMessageDialog(null, "Ticket " + id + " enviado com sucesso.");
                        connection.close();
                        dispose();
                    }
                }
            }catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao enviar o ticket: " + ex.getMessage());
            }
        });
        buttonPanel.add(salvarButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
    public void setNomeUsuario(String nomeUsuario){
        this.nomeUsuario = nomeUsuario;
    }
}
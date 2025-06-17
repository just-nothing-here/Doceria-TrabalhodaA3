import java.awt.*;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class SistemaInterface extends JFrame {
    private JTextField idField, nomeField, descField, precoField, unidadesField;
    private JTextArea outputArea;
    private ProdutoDAO dao;
    private JLabel clockLabel;

    public SistemaInterface() {
        super("Doceria NegoBom - Cadastro de Produtos");

        try {
            dao = new ProdutoDAO();
        } catch (Exception e) {
            JOptionPane.showInputDialog("Erro ao conectar com o banco de dados: " + e.getMessage());
            System.exit(1);
        }

        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(255, 250, 240));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(255, 228, 225));
        headerPanel.setPreferredSize(new Dimension(0, 50));

        JLabel title = new JLabel("Sistema de Cadastro doceria", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.add(title, BorderLayout.CENTER);

        clockLabel = new JLabel("", JLabel.RIGHT);
        clockLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        headerPanel.add(clockLabel, BorderLayout.EAST);

        Timer timer = new Timer(1000, e -> {
            String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            clockLabel.setText("  " + time + "  ");
        });
        timer.start();

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(new Color(255, 248, 220));
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados do Produto"));

        idField = new JTextField();
        nomeField = new JTextField();
        descField = new JTextField();
        precoField = new JTextField();
        unidadesField = new JTextField();

        formPanel.add(new JLabel("ID (editar/deletar):")); formPanel.add(idField);
        formPanel.add(new JLabel("Nome:"));               formPanel.add(nomeField);
        formPanel.add(new JLabel("Descrição:"));          formPanel.add(descField);
        formPanel.add(new JLabel("Preço:"));              formPanel.add(precoField);
        formPanel.add(new JLabel("Unidades:"));           formPanel.add(unidadesField);

        JButton addButton     = new JButton("Adicionar");
        JButton listButton    = new JButton("Listar");
        JButton viewButton    = new JButton("Ver por ID");
        JButton updateButton  = new JButton("Atualizar");
        JButton deleteButton  = new JButton("Deletar");
        JButton confirmButton = new JButton("Confirmar");
        JButton cancelButton  = new JButton("Cancelar");

        JPanel buttonPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        buttonPanel.setBackground(new Color(255, 250, 240));
        buttonPanel.add(addButton);
        buttonPanel.add(listButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(new JLabel());
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(new JLabel());

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(headerPanel, BorderLayout.NORTH);
        northPanel.add(formPanel, BorderLayout.CENTER);
        northPanel.add(buttonPanel, BorderLayout.EAST);
        northPanel.setPreferredSize(new Dimension(0, 300));
        add(northPanel, BorderLayout.NORTH);

        outputArea = new JTextArea(12, 60);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputArea.setEditable(false);
        outputArea.setBorder(BorderFactory.createTitledBorder("Informações dos Produtos"));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> adicionarProduto());
        listButton.addActionListener(e -> listarProdutos());
        viewButton.addActionListener(e -> mostrarProdutoPorId());
        updateButton.addActionListener(e -> atualizarProduto());
        deleteButton.addActionListener(e -> deletarProduto());
        confirmButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Ação confirmada!"));
        cancelButton.addActionListener(e -> limparCampos());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @SuppressWarnings("UseSpecificCatch")
    private void adicionarProduto() {
        try {
            String nome = nomeField.getText();
            String descricao = descField.getText();
            double preco = Double.parseDouble(precoField.getText());
            int unidades = Integer.parseInt(unidadesField.getText());

            Produto novo = new Produto(nome, descricao, preco, unidades);
            dao.adicionar(novo);
            outputArea.setText("Produto adicionado com sucesso!");
            limparCampos();
        } catch (Exception ex) {
            outputArea.setText("Erro ao adicionar: " + ex.getMessage());
        }
    }

    private void listarProdutos() {
        try {
            StringBuilder sb = new StringBuilder();
            for (Produto p : dao.listar()) {
                sb.append(p.exibirDetalhes()).append("\n\n");
            }
            outputArea.setText(sb.toString());
        } catch (SQLException ex) {
            outputArea.setText("Erro ao listar: " + ex.getMessage());
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    private void mostrarProdutoPorId() {
        try {
            int id = Integer.parseInt(idField.getText());
            Produto p = dao.buscarPorId(id);
            if (p != null) outputArea.setText(p.exibirDetalhes());
            else outputArea.setText("Produto com ID " + id + " não encontrado.");
        } catch (Exception ex) {
            outputArea.setText("Erro ao buscar: " + ex.getMessage());
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    private void atualizarProduto() {
        try {
            int id = Integer.parseInt(idField.getText());
            String nome = nomeField.getText();
            String descricao = descField.getText();
            double preco = Double.parseDouble(precoField.getText());
            int unidades = Integer.parseInt(unidadesField.getText());

            boolean sucesso = dao.atualizar(id, new Produto(nome, descricao, preco, unidades));
            if (sucesso) {
                outputArea.setText("Produto atualizado com sucesso!");
                limparCampos();
            } else {
                outputArea.setText("Produto com ID " + id + " não encontrado.");
            }
        } catch (Exception ex) {
            outputArea.setText("Erro ao atualizar: " + ex.getMessage());
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    private void deletarProduto() {
        try {
            int id = Integer.parseInt(idField.getText());
            boolean sucesso = dao.deletar(id);
            if (sucesso) {
                outputArea.setText("Produto deletado com sucesso!");
                limparCampos();
            } else {
                outputArea.setText("Produto com ID " + id + " não encontrado.");
            }
        } catch (Exception ex) {
            outputArea.setText("Erro ao deletar: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        idField.setText("");
        nomeField.setText("");
        descField.setText("");
        precoField.setText("");
        unidadesField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SistemaInterface::new);
    }

    public JTextField getIdField() {
        return idField;
    }

    public void setIdField(JTextField idField) {
        this.idField = idField;
    }

    public JTextField getNomeField() {
        return nomeField;
    }

    public void setNomeField(JTextField nomeField) {
        this.nomeField = nomeField;
    }

    public JTextField getDescField() {
        return descField;
    }

    public void setDescField(JTextField descField) {
        this.descField = descField;
    }

    public JTextField getPrecoField() {
        return precoField;
    }

    public void setPrecoField(JTextField precoField) {
        this.precoField = precoField;
    }

    public JTextField getUnidadesField() {
        return unidadesField;
    }

    public void setUnidadesField(JTextField unidadesField) {
        this.unidadesField = unidadesField;
    }

    public JTextArea getOutputArea() {
        return outputArea;
    }

    public void setOutputArea(JTextArea outputArea) {
        this.outputArea = outputArea;
    }
}

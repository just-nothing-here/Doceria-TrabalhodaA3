import java.sql.*;
import java.util.ArrayList;

@SuppressWarnings("ConvertToTryWithResources")

public class ProdutoDAO {
    private Connection conn;

    public ProdutoDAO() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/doceria", "root", "root");
    }

    public void adicionar(Produto p) throws SQLException {
        String sql = "INSERT INTO produtos (nome, descricao, preco, unidades) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, p.getNome());
        stmt.setString(2, p.getDescricao());
        stmt.setDouble(3, p.getPreco());
        stmt.setInt(4, p.getUnidades());
        stmt.executeUpdate();
        stmt.close();
    }

    public ArrayList<Produto> listar() throws SQLException {
        ArrayList<Produto> lista = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM produtos");

        while (rs.next()) {
            Produto p = new Produto(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("descricao"),
                rs.getDouble("preco"),
                rs.getInt("unidades")
            );
            lista.add(p);
        }

        rs.close();
        stmt.close();
        return lista;
    }

    public Produto buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Produto p = new Produto(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("descricao"),
                rs.getDouble("preco"),
                rs.getInt("unidades")
            );
            rs.close();
            stmt.close();
            return p;
        }

        rs.close();
        stmt.close();
        return null;
    }

    public void atualizar(Produto p) throws SQLException {
        String sql = "UPDATE produtos SET nome = ?, descricao = ?, preco = ?, unidades = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, p.getNome());
        stmt.setString(2, p.getDescricao());
        stmt.setDouble(3, p.getPreco());
        stmt.setInt(4, p.getUnidades());
        stmt.setInt(5, p.getId());
        stmt.executeUpdate();
        stmt.close();
    }

    public boolean deletar(int id) throws SQLException {
        String sql = "DELETE FROM produtos WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        stmt.close();
        return false;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public boolean atualizar(int id, Produto atualizado) {
        throw new UnsupportedOperationException("Unimplemented method 'atualizar'");
    }
}

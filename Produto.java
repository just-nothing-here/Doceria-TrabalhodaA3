public class Produto extends Item implements Exibivel {
    private String descricao;
    private int unidades;

    public Produto(int id, String nome, String descricao, double preco, int unidades) {
        super(id, nome, preco);
        this.descricao = descricao;
        this.unidades = unidades;
    }

    public Produto(String nome, String descricao, double preco, int unidades) {
        this(0, nome, descricao, preco, unidades);
    }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public int getUnidades() { return unidades; }
    public void setUnidades(int unidades) { this.unidades = unidades; }

    @Override
    public String exibirDetalhes() {
        return String.format("Nome: %s\nPreço: R$ %.2f\nDescrição: %s\nUnidades disponíveis: %d",
                             nome, preco, descricao, unidades);
    }

    @Override
    public String resumo() {
        return String.format("ID: %d | %s - R$ %.2f", id, nome, preco);
    }
}

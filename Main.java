import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("ConvertToTryWithResources")

public class Main {
    public static void saudacao() {
        int hora = LocalTime.now().getHour();
        if (hora < 12) System.out.println("Bom dia!");
        else if (hora < 18) System.out.println("Boa tarde!");
        else System.out.println("Boa noite!");
    }

    public static void menu() {
        System.out.println("+----------------------------------------------+");
        System.out.println("|  Sistema de registro de produtos da doceria  |");
        System.out.println("+----------------------------------------------+");
        System.out.println("| 1 - Adicionar produto                        |");
        System.out.println("| 2 - Listar produtos                          |");
        System.out.println("| 3 - Mostrar produto por ID                   |");
        System.out.println("| 4 - Atualizar produto                        |");
        System.out.println("| 5 - Deletar produto                          |");
        System.out.println("| 0 - Sair                                     |");
        System.out.println("+----------------------------------------------+");
    }

    public static void main(String[] args) throws Exception {
        Scanner sc1 = new Scanner(System.in);
        ProdutoDAO dao = new ProdutoDAO();
        saudacao();

        int op;
        do {
            menu();
            System.out.print("Escolha uma opção: ");
            op = sc1.nextInt();

            switch (op) {
                case 1 -> {
                    sc1.nextLine();
                    System.out.print("Nome: ");
                    String nome = sc1.nextLine();
                    System.out.print("Descrição: ");
                    String desc = sc1.nextLine();
                    System.out.print("Preço: ");
                    double preco = sc1.nextDouble();
                    System.out.print("Unidades: ");
                    int unidades = sc1.nextInt();
                    dao.adicionar(new Produto(nome, desc, preco, unidades));
                }

                case 2 -> {
                    ArrayList<Produto> produtos = dao.listar();
                    for (Produto p : produtos) {
                        System.out.println(p.resumo());
                    }
                }

                case 3 -> {
                    System.out.print("Digite o ID: ");
                    int id = sc1.nextInt();
                    Produto p = dao.buscarPorId(id);
                    if (p != null) System.out.println(p.exibirDetalhes());
                    else System.out.println("Produto não encontrado.");
                }

                case 4 -> {
                    System.out.print("ID do produto a atualizar: ");
                    int idAtualiza = sc1.nextInt();
                    sc1.nextLine();
                    Produto existente = dao.buscarPorId(idAtualiza);
                    if (existente != null) {
                        System.out.print("Novo nome: ");
                        String novoNome = sc1.nextLine();
                        System.out.print("Nova descrição: ");
                        String novaDesc = sc1.nextLine();
                        System.out.print("Novo preço: ");
                        double novoPreco = sc1.nextDouble();
                        System.out.print("Novas unidades: ");
                        int novasUnidades = sc1.nextInt();
                        Produto atualizado = new Produto(idAtualiza, novoNome, novaDesc, novoPreco, novasUnidades);
                        dao.atualizar(atualizado);
                        System.out.println("Produto atualizado com sucesso!");
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                }

                case 5 -> {
                    System.out.print("ID para deletar: ");
                    int delId = sc1.nextInt();
                    dao.deletar(delId);
                }

                case 0 -> System.out.println("Saindo...");

                default -> System.out.println("Opção inválida.");
            }
        } while (op != 0);

        sc1.close();
    }
}

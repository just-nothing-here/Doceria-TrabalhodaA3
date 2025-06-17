CREATE DATABASE IF NOT EXISTS doceria;
USE doceria;

DROP TABLE IF EXISTS produtos;

CREATE TABLE produtos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    descricao TEXT,
    preco DECIMAL(10,2),
    unidades INT
);

INSERT INTO produtos (nome, descricao, preco, unidades) VALUES
("Trufa", "Trufinha de Chocolate ao leite", 6.0, 15),
('Cupcake de Morango', 'Cobertura com chantilly e recheio de morango', 5.50, 10),
('Brownie de Nutella', 'Brownie recheado com Nutella e castanhas', 7.00, 15),
('Torta de Limão', 'Massa crocante com creme cítrico e merengue', 10.00, 8),
('Palha Italiana', 'Doce gelado de brigadeiro com biscoito', 4.50, 12),
('Pudim de Leite Condensado', 'Clássico com calda de caramelo', 6.00, 10),
('Bolo de Chocolate com Morango', 'Bolo fofo com recheio de morango e cobertura de chocolate', 12.00, 6),
('Beijinho', 'Doce de coco com leite condensado e açúcar', 2.50, 25),
('Brigadeiro Gourmet', 'Brigadeiro com chocolate belga e granulado especial', 3.50, 18),
('Mini Cheesecake de Frutas Vermelhas', 'Cheesecake cremoso com calda artesanal de frutas vermelhas', 9.50, 7);
package org.example.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "ItensEstoque")
public class ItemEstoque {

    public ItemEstoque(Estoque estoque, Produto produto, int quantidadeAtual, int quantidadeMinima) {
        this.estoque = estoque;
        this.produto = produto;
        this.quantidadeAtual = quantidadeAtual;
        this.quantidadeMinima = quantidadeMinima;
    }

    public ItemEstoque() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "estoque_id")
    private Estoque estoque;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private int quantidadeAtual;

    private int quantidadeMinima;

    public Integer getId() {
        return id;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidadeAtual() {
        return quantidadeAtual;
    }

    public void setQuantidadeAtual(int quantidadeAtual) {
        this.quantidadeAtual = quantidadeAtual;
    }

    public int getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(int quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }
}

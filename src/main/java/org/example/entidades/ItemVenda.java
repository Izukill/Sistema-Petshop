package org.example.entidades;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ItensVenda")
public class ItemVenda {

    public ItemVenda(Produto produto, int quantidade) {
        this.precoUnitario = produto.getPrecoBase();
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public ItemVenda() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;

    private int quantidade;

    private BigDecimal precoUnitario;

    public BigDecimal calcularSubTotal(){

        if(precoUnitario == null) return BigDecimal.ZERO;


        return this.precoUnitario.multiply(BigDecimal.valueOf(quantidade));

    }

    public Integer getId() {
        return id;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }
}

package org.example.entidades;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Vendas")
public class Venda {

    public Venda(Cliente cliente, LocalDate data, Funcionario funcionario, List<ItemVenda> itens, List<Servico> servicos) {
        this.cliente = cliente;
        this.data = data;
        this.funcionario = funcionario;
        this.itens = itens;
        this.servicos = servicos;
        this.valorTotal= BigDecimal.ZERO;
    }

    public Venda(Cliente cliente, LocalDate data, Funcionario funcionario) {
        this.cliente = cliente;
        this.data = data;
        this.funcionario = funcionario;
    }

    public Venda() {
        this.itens = new ArrayList<>();
        this.servicos = new ArrayList<>();
        this.valorTotal = BigDecimal.ZERO;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private LocalDate data;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ItemVenda> itens = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "venda_id")
    private List<Servico> servicos = new ArrayList<>();

    private BigDecimal valorTotal;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    public BigDecimal calcularPrecoFinal(){
        BigDecimal total = BigDecimal.ZERO; // Começa com zero

        if (itens != null) {
            for (ItemVenda i : itens) {
                total = total.add(i.calcularSubTotal());
            }
        }
        if (servicos != null) {
            for (Servico s : servicos) {
                    total = total.add(s.getPreco());
            }
        }

        this.valorTotal = total;
        return total;
    }



    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getId() {
        return id;
    }


    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void adicionarServico(Servico s){
        this.servicos.add(s);
        calcularPrecoFinal();
    }

    public void adicionarProduto(ItemVenda p){
        this.itens.add(p);
        p.setVenda(this); // Vincula o item a esta venda
        calcularPrecoFinal();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}

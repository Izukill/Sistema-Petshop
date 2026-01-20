package org.example.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Clientes")
public class Cliente extends Pessoa{

    public Cliente(Boolean ativo, String cpf, String email, String endereco, String nome, String telefone, LocalDate dataCadastro, String observacoes) {
        super(ativo, cpf, email, endereco, nome, telefone);
        this.dataCadastro = dataCadastro;
        this.observacoes = observacoes;
    }

    public Cliente() {
    }

    private LocalDate dataCadastro;

    private String observacoes;

    @OneToMany(mappedBy = "dono", fetch = FetchType.LAZY)
    private List<Pet> pets = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Venda> historicoCompras = new ArrayList<>();

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public List<Venda> getHistoricoCompras() {
        return historicoCompras;
    }

    public void setHistoricoCompras(List<Venda> historicoCompras) {
        this.historicoCompras = historicoCompras;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}

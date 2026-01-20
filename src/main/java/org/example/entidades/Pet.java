package org.example.entidades;

import jakarta.persistence.*;
import org.example.dao.EntidadeAtivavel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Pets")
public class Pet implements EntidadeAtivavel {

    public Pet(String especie, LocalDate nascimento, String nome, String raca, Cliente dono) {
        this.especie = especie;
        this.nascimento = nascimento;
        this.nome = nome;
        this.raca = raca;
        this.dono= dono;
        this.ativo = true;
    }

    public Pet() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente dono;

    @OneToMany(mappedBy = "pet")
    private List<Servico> historicoServicos = new ArrayList<>();

    private String nome;

    private String especie;

    private String raca;

    private LocalDate nascimento;

    private boolean ativo;


    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public Integer getId() {
        return id;
    }

    public Cliente getDono() {
        return dono;
    }

    public void setDono(Cliente dono) {
        this.dono = dono;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public List<Servico> getHistoricoServicos() {
        return historicoServicos;
    }

    @Override
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public Boolean getAtivo() {
        return ativo;
    }
}

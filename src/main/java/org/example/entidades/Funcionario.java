package org.example.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "Funcionarios")
public class Funcionario extends Pessoa{


    public Funcionario(Boolean ativo, String email, String endereco, String nome, String telefone, String cargo, String especializacao, String matricula) {
        super(ativo, email, endereco, nome, telefone);
        this.cargo = cargo;
        this.especializacao = especializacao;
        this.matricula = matricula;
    }

    public Funcionario() {
    }

    private String cargo;

    private String matricula;

    private String especializacao;

    @OneToMany(mappedBy = "funcionario")
    private List<Venda> vendasRealizadas;


    @OneToMany(mappedBy = "funcionario")
    private List<Servico> servicosRealizados;


    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEspecializacao() {
        return especializacao;
    }

    public void setEspecializacao(String especializacao) {
        this.especializacao = especializacao;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}

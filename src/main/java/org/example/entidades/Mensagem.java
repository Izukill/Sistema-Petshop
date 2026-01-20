package org.example.entidades;

import java.time.LocalDate;

public class Mensagem {

    public Mensagem(String conteudo, LocalDate dataEnvio, Cliente destinatario, String observacao) {
        this.conteudo = conteudo;
        this.dataEnvio = dataEnvio;
        this.destinatario = destinatario;
        this.observacao = observacao;
    }

    public Mensagem() {
    }

    private Cliente destinatario;

    private String conteudo;

    private LocalDate dataEnvio;

    private String observacao;

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDate getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDate dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Cliente getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Cliente destinatario) {
        this.destinatario = destinatario;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}

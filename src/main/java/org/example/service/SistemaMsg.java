package org.example.service;

import org.example.entidades.Cliente;
import org.example.entidades.Mensagem;
import org.example.entidades.Pet;
import org.example.entidades.Servico;

import java.time.LocalDate;

public class SistemaMsg {

    private Mensagem mensagem;

    public void enviarMensagemAutomática(Mensagem msg){
        if (msg == null || msg.getDestinatario() == null) {
            System.out.println("[ERRO] Mensagem inválida ou sem destinatário.");
        }else {


            Cliente destinatario = msg.getDestinatario();

            System.out.println("\n=================================================");
            System.out.println(">>> [NOVA MENSAGEM AUTOMÁTICA ENVIADA] <<<");
            System.out.println("-------------------------------------------------");
            System.out.println("Data: " + msg.getDataEnvio());
            System.out.println("Para: " + destinatario.getNome());

            System.out.println("Contato: " + destinatario.getEmail());
            System.out.println("-------------------------------------------------");
            System.out.println("MENSAGEM:");
            System.out.println(msg.getConteudo());
            System.out.println("=================================================\n");

        }

    }

    public void notificarStatusServico(Servico servico){

        Pet pet = servico.getPet();

        if (pet == null || pet.getDono() == null) {
            System.out.println("[AVISO] Não foi possível notificar: Pet ou Dono não identificados no serviço.");
            return;
        }

        Cliente dono = pet.getDono();


        String textoMensagem = String.format(
                "Olá %s! \nO serviço de '%s' para o pet %s mudou de status.\nStatus Atual: %s.\nObservação: %s",
                dono.getNome(),
                servico.getDescricao(),
                pet.getNome(),
                servico.getStatus(), // Ex:"CONCLUIDO", "EM ANDAMENTO"
                servico.getObservacao() != null ? servico.getObservacao() : "Sem observações."
        );


        Mensagem msg = new Mensagem(
                textoMensagem,
                LocalDate.now(),
                dono,
                "Notificação Automática de Status"
        );


        this.enviarMensagemAutomática(msg);
    }


}

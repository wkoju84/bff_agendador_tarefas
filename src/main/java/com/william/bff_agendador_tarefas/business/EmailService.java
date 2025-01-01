package com.william.bff_agendador_tarefas.business;



import com.william.bff_agendador_tarefas.business.dtos.out.TarefaDTOResponse;
import com.william.bff_agendador_tarefas.infrastructure.client.EmailClient;
import org.springframework.stereotype.Service;

@Service

public class EmailService {

    private final EmailClient client;

    public EmailService(EmailClient client) {
        this.client = client;
    }


    public void enviaEmail(TarefaDTOResponse dto){
        client.enviarEmail(dto);
    }

}

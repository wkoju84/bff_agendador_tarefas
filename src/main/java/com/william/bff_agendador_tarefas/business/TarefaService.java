package com.william.bff_agendador_tarefas.business;



import com.william.bff_agendador_tarefas.business.dtos.in.TarefaDTORequest;
import com.william.bff_agendador_tarefas.business.dtos.out.TarefaDTOResponse;
import com.william.bff_agendador_tarefas.business.enums.StatusNotificacaoEnum;
import com.william.bff_agendador_tarefas.infrastructure.client.TarefasClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TarefaService {

    private final TarefasClient tarefasClient;

    public TarefaService(TarefasClient tarefasClient) {
        this.tarefasClient = tarefasClient;
    }

    public TarefaDTOResponse gravarTarefa(TarefaDTORequest dto, String token){


        return tarefasClient.gravarTarefas(dto, token);
    }

    public List<TarefaDTOResponse> buscarTarefasAgendadasPorPeriodo(LocalDateTime dataInicial,
                                                                    LocalDateTime dataFinal,
                                                                    String token){

        return tarefasClient.buscaListaDeTarefasPorPeriodo(dataInicial, dataFinal, token);
    }

    public List<TarefaDTOResponse> buscaTarefaPorEmail(String token){

        return tarefasClient.buscaTarefasPorEmail(token);
    }

    public void deletaTarefaPorId(String id, String token){

       tarefasClient.deletaTarefaPorId(id, token);
    }

    /*Metodo de alteração de Status*/
    public TarefaDTOResponse alteraStatus(StatusNotificacaoEnum status, String id, String token){

       return tarefasClient.alteraStatusNotificacao(status, id, token);
    }

    public TarefaDTOResponse updateTarefas(TarefaDTORequest dto, String id, String token){

        return tarefasClient.updateTarefas(dto, id, token).getBody();
    }
}

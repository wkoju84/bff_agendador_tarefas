package com.william.bff_agendador_tarefas.infrastructure.client;



import com.william.bff_agendador_tarefas.business.dtos.in.TarefaDTORequest;
import com.william.bff_agendador_tarefas.business.dtos.out.TarefaDTOResponse;
import com.william.bff_agendador_tarefas.business.enums.StatusNotificacaoEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "agendador-tarefas", url = "${agendador-tarefas.url}")
public interface TarefasClient {

    @PostMapping
    TarefaDTOResponse gravarTarefas(@RequestBody TarefaDTORequest dto,
                                    @RequestHeader("Authorization") String token);

    @GetMapping("/eventos")
    List<TarefaDTOResponse> buscaListaDeTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime  dataFinal,
            @RequestHeader ("Authorization") String token);

    @GetMapping
    List<TarefaDTOResponse> buscaTarefasPorEmail(@RequestHeader ("Authorization") String token);

    @DeleteMapping
    void deletaTarefaPorId(@RequestParam("id") String id, String token);

    @PatchMapping
    TarefaDTOResponse alteraStatusNotificacao(@RequestParam("status") StatusNotificacaoEnum status,
                                              @RequestParam("id") String id, String token);

    @PutMapping
    public ResponseEntity<TarefaDTOResponse> updateTarefas(@RequestBody TarefaDTORequest dto,
                                                           @RequestParam("id") String id,
                                                           @RequestHeader ("Authorization") String token);
}


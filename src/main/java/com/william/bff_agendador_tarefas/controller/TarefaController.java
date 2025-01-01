package com.william.bff_agendador_tarefas.controller;



import com.william.bff_agendador_tarefas.business.TarefaService;
import com.william.bff_agendador_tarefas.business.dtos.in.TarefaDTORequest;
import com.william.bff_agendador_tarefas.business.dtos.out.TarefaDTOResponse;
import com.william.bff_agendador_tarefas.business.enums.StatusNotificacaoEnum;
import com.william.bff_agendador_tarefas.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/tarefas")
@Tag(name = "Tarefas", description = "Cadastra tarefas de usuários")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping
    @Operation(summary = "Salvar tarefas de usuários", description = "Cria uma nova tarefa")
    @ApiResponse(responseCode = "200", description = "Tarefa salva com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TarefaDTOResponse> gravarTarefas(@RequestBody TarefaDTORequest dto,
                                                           @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(tarefaService.gravarTarefa(dto, token));
    }

    @GetMapping("/eventos")
    @Operation(summary = "Busca tarefas por período", description = "Busca tarefa cadastradas por período")
    @ApiResponse(responseCode = "200", description = "Tarefa encontradas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<List<TarefaDTOResponse>> buscaListaDeTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime  dataFinal,
            @RequestHeader ("Authorization") String token){

        return ResponseEntity.ok(tarefaService.buscarTarefasAgendadasPorPeriodo(dataInicial, dataFinal, token));
    }

    @GetMapping
    @Operation(summary = "Busca lista de tarefas por email de usuários",
            description = "Busca tarefa cadastradas por usuário")
    @ApiResponse(responseCode = "200", description = "Tarefa encontradas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<List<TarefaDTOResponse>> buscaTarefasPorEmail(@RequestHeader ("Authorization") String token){

        List<TarefaDTOResponse> tarefas = tarefaService.buscaTarefaPorEmail(token);
        return ResponseEntity.ok(tarefas);
    }

    @DeleteMapping
    @Operation(summary = "Deleta tarefas por Id", description = "Deleta tarefas cadastradas por Id")
    @ApiResponse(responseCode = "200", description = "Tarefa deletada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<Void> deletaTarefaPorId(@RequestParam("id") String id,
                                                  @RequestHeader ("Authorization") String token){

        tarefaService.deletaTarefaPorId(id, token);

        return ResponseEntity.ok().build();
    }

    @PatchMapping
    @Operation(summary = "Altera status de tarefas", description = "Altera status das tarefa cadastradas")
    @ApiResponse(responseCode = "200", description = "Status da tarefa alterado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TarefaDTOResponse> alteraStatusNotificacao(@RequestParam("status") StatusNotificacaoEnum status,
                                                                     @RequestParam("id") String id,
                                                                     @RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(tarefaService.alteraStatus(status, id, token));
    }

    @PutMapping
    @Operation(summary = "Altera dados de tarefas", description = "Altera dados das tarefa cadastradas")
    @ApiResponse(responseCode = "200", description = "Tarefa alteradas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TarefaDTOResponse> updateTarefas(@RequestBody TarefaDTORequest dto,
                                                           @RequestParam("id") String id,
                                                           @RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(tarefaService.updateTarefas(dto, id, token));
    }
}

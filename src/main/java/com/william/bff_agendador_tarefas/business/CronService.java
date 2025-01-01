package com.william.bff_agendador_tarefas.business;


import com.william.bff_agendador_tarefas.business.dtos.in.LoginRequestDTO;
import com.william.bff_agendador_tarefas.business.dtos.out.TarefaDTOResponse;
import com.william.bff_agendador_tarefas.business.enums.StatusNotificacaoEnum;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class CronService {

    private final TarefaService tarefaService;
    private final EmailService emailService;
    private final UsuarioService usuarioService;

    public CronService(TarefaService tarefaService, EmailService emailService, UsuarioService usuarioService) {
        this.tarefaService = tarefaService;
        this.emailService = emailService;
        this.usuarioService = usuarioService;
    }

    @Value("${usuario.email}")
    private String email;

    @Value("${usuario.senha}")
    private String senha;

    @Scheduled(cron = "${cron.horario}")
    public void buscarTarefasNaProximaHora(){

        String token = login(converterParaRequestDTO());

        //Adianta 1 hora a mais que a hora atual
        LocalDateTime horaFutura = LocalDateTime.now().plusHours(1);
        LocalDateTime horaFuturaMaisCinco = LocalDateTime.now().plusHours(1).plusMinutes(5);

        List<TarefaDTOResponse> listaTarefas = tarefaService.buscarTarefasAgendadasPorPeriodo(horaFutura,
                horaFuturaMaisCinco, token);

        listaTarefas.forEach(tarefa -> {
            emailService.enviaEmail(tarefa);
            TarefaDTOResponse listaTarefa = tarefaService.alteraStatus(StatusNotificacaoEnum.NOTIFICADO,
                    tarefa.getId(), token);
        });
    }

    public String login(LoginRequestDTO dto){
        return usuarioService.loginUsuario(dto);
    }

    public LoginRequestDTO converterParaRequestDTO(){
        return LoginRequestDTO.builder()
                .email(email)
                .senha(senha)
                .build();
    }
}

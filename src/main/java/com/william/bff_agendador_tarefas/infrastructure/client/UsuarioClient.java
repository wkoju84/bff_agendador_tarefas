package com.william.bff_agendador_tarefas.infrastructure.client;





import com.william.bff_agendador_tarefas.business.dtos.in.EnderecoDTORequest;
import com.william.bff_agendador_tarefas.business.dtos.in.LoginRequestDTO;
import com.william.bff_agendador_tarefas.business.dtos.in.TelefoneDTORequest;
import com.william.bff_agendador_tarefas.business.dtos.in.UsuarioDTORequest;
import com.william.bff_agendador_tarefas.business.dtos.out.EnderecoDTOResponse;
import com.william.bff_agendador_tarefas.business.dtos.out.TelefoneDTOResponse;
import com.william.bff_agendador_tarefas.business.dtos.out.UsuarioDTOResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuario", url = "${usuario.url}")
public interface UsuarioClient {

    @GetMapping
    UsuarioDTOResponse buscaUsuarioPorEmail(@RequestParam("email") String email,
                                            @RequestHeader("Authorization") String token);

    @PostMapping
    UsuarioDTOResponse salvaUsuario(@RequestBody UsuarioDTORequest usuarioDTO);

    @PostMapping("/login")
    String login(@RequestBody LoginRequestDTO usuarioDTO);


    @DeleteMapping("/{email}")
    void deletaUsuarioPorEmail(@PathVariable String email,
                               @RequestHeader("Authorization") String token);

    @PutMapping
    UsuarioDTOResponse atualizaDadosUsuario(@RequestBody UsuarioDTORequest dto,
                                           @RequestHeader("Authorization") String token);


@PutMapping("/endereco")
EnderecoDTOResponse atualizaEndereco(@RequestBody EnderecoDTORequest dto,
                                     @RequestHeader("id") Long id,
                                     @RequestHeader("Authorization") String token);

@PutMapping("/telefone")
TelefoneDTOResponse atualizaTelefone(@RequestBody TelefoneDTORequest dto,
                                     @RequestHeader("id") Long id,
                                     @RequestHeader("Authorization") String token);

@PostMapping("/endereco")
EnderecoDTOResponse cadastraEndereco(@RequestBody EnderecoDTORequest dto,
                                    @RequestHeader("Authorization") String token);

@PostMapping("/telefone")
TelefoneDTOResponse cadastraTelefone(@RequestBody TelefoneDTORequest dto,
                                    @RequestHeader("Authorization") String token);
}


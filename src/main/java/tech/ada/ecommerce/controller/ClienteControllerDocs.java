package tech.ada.ecommerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import tech.ada.ecommerce.dto.ClienteDTO;
import tech.ada.ecommerce.dto.ClienteEnderecoDTO;
import tech.ada.ecommerce.model.Cliente;
import tech.ada.ecommerce.response.GenericResponse;

import java.util.HashMap;
import java.util.List;

public interface ClienteControllerDocs {

    @Operation(summary = "Busca de todos os clientes", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso!", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
            }),
            @ApiResponse(responseCode = "401", description = "Usuário não logado!", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado!", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor!",
            content = @Content)
    })
    List<Cliente> getClientes();

    @Operation(summary = "Busca de todos os clientes ativos", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso!", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
            }),
            @ApiResponse(responseCode = "401", description = "Usuário não logado!", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado!", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor!",
                    content = @Content)
    })
    List<Cliente> getClientesAtivos();

    @Operation(summary = "Inserindo novo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente registrado com sucesso!", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Ocorreu um erro no lado do cliente!", content = @Content),
            @ApiResponse(responseCode = "401", description = "Usuário não logado!", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado!", content = @Content),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno no servidor!",
                    content = @Content)
    })
    ResponseEntity<GenericResponse> saveCliente(@RequestBody ClienteDTO cliente);

    ResponseEntity<ClienteDTO> atualizarCliente(@RequestBody ClienteDTO cliente);
    ResponseEntity<ClienteDTO> getClienteById(@PathVariable("id") Long idCliente);
    ResponseEntity<List<Cliente>> getClienteByNome(@RequestParam("nome") String nome);
    ResponseEntity<Void> deleteCliente(@PathVariable("id") Long id);
    ResponseEntity<Void> ativarDesativarCliente(@PathVariable("id") Long id, @RequestParam("ativo") boolean ativo);
    ResponseEntity<String> alterarSenha(@PathVariable("id") Long id,
                                        @RequestBody HashMap<String, String> senhas);
    ResponseEntity<String> adicionarEndereco(@RequestBody ClienteEnderecoDTO clienteEndereco);
    ResponseEntity<String> adicionarRole(@RequestParam("idCliente") Long idCliente,
                                         @RequestParam("idRole") Long idRole);

}

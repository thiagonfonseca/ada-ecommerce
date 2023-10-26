package tech.ada.ecommerce.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.ada.ecommerce.dto.ClienteDTO;
import tech.ada.ecommerce.dto.ClienteEnderecoDTO;
import tech.ada.ecommerce.exception.CustomExceptionHandler;
import tech.ada.ecommerce.model.Cliente;
import tech.ada.ecommerce.model.ClienteEndereco;
import tech.ada.ecommerce.model.Endereco;
import tech.ada.ecommerce.response.GenericResponse;
import tech.ada.ecommerce.service.ClienteService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/cliente")
public class ClienteController implements ClienteControllerDocs {


//    @Autowired
//    ClienteService clienteService;

    ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

//    @RequestMapping(method = RequestMethod.GET)
    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Cliente> getClientes() {
        return clienteService.buscarTodosOsClientes();
    }

    @GetMapping("/ativos")
    public List<Cliente> getClientesAtivos() {
        return clienteService.buscarClientesAtivos();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GenericResponse> saveCliente(@RequestBody ClienteDTO cliente) {
        try {
            ClienteDTO savedCliente = clienteService.salvarCliente(cliente);
            if (savedCliente != null) {
                GenericResponse response = new GenericResponse();
                response.setStatus(HttpStatus.CREATED.value());
                response.setData(savedCliente);
                response.setMessage("Cliente criado com sucesso!");
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ConstraintViolationException exception) {
            CustomExceptionHandler ex = new CustomExceptionHandler();
            return new ResponseEntity<>(ex.processException(exception), HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClienteDTO> atualizarCliente(@RequestBody ClienteDTO cliente) {
        try {
            ClienteDTO savedCliente = clienteService.atualizarCliente(cliente);
            if (savedCliente != null)
                return new ResponseEntity<>(savedCliente, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable("id") Long idCliente) {
        return new ResponseEntity<>(clienteService.buscarPorId(idCliente), HttpStatus.OK);
    }

    @GetMapping("/nome")
    public ResponseEntity<List<Cliente>> getClienteByNome(@RequestParam("nome") String nome) {
        return new ResponseEntity<>(clienteService.buscarPorNome(nome), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCliente(@PathVariable("id") Long id) {
        clienteService.deletarCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> ativarDesativarCliente(@PathVariable("id") Long id, @RequestParam("ativo") boolean ativo) {
        clienteService.ativarDesativarCliente(ativo, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/senha/{id}")
    public ResponseEntity<String> alterarSenha(@PathVariable("id") Long id,
                                                 @RequestBody HashMap<String, String> senhas) {
        try {
            return clienteService.alterarSenha(id, senhas);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/endereco")
    public ResponseEntity<String> adicionarEndereco(@RequestBody ClienteEnderecoDTO clienteEndereco) {
        clienteService.adicionarEndereco(clienteEndereco);
        return new ResponseEntity<>("Endereco adicionado com sucesso", HttpStatus.OK);
    }

    @PatchMapping("/role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> adicionarRole(@RequestParam("idCliente") Long idCliente,
                                                @RequestParam("idRole") Long idRole) {
        return clienteService.adicionarRole(idCliente, idRole);
    }

//    @PutMapping("")
//    public ResponseEntity<ClienteDTO> atualizarCliente(@RequestBody ClienteDTO cliente) {
//        try {
//            ClienteDTO savedCliente = clienteService.salvarCliente(cliente);
//            if (savedCliente != null)
//                return new ResponseEntity<>(savedCliente, HttpStatus.CREATED);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (Exception exception) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }



}

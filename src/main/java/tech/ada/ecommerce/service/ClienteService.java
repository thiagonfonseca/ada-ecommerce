package tech.ada.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ada.ecommerce.dto.ClienteDTO;
import tech.ada.ecommerce.model.Cliente;
import tech.ada.ecommerce.repository.ClienteRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

//    @Autowired
    ClienteRepository clienteRepo;

    public ClienteService(ClienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }


    public List<Cliente> buscarTodosOsClientes() {
        List<Cliente> clientes = clienteRepo.findAll();
        return clientes;
    }

    public List<Cliente> buscarClientesAtivos() {
        return clienteRepo.findByAtivo(true);
    }

    public ClienteDTO salvarCliente(ClienteDTO clienteDTO) {
        try {
            DateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
            Date dataNascimento = dtf.parse(clienteDTO.getDataNascimento());
            Cliente cliente;
            if (clienteDTO.getId() != null) {
                cliente = new Cliente(clienteDTO.getId(), clienteDTO.getNomeCompleto(),
                        dataNascimento, clienteDTO.getCpf(), clienteDTO.getEmail(),
                        clienteDTO.getSenha(), clienteDTO.isAtivo());
            } else {
                cliente = new Cliente(clienteDTO.getNomeCompleto(), dataNascimento,
                        clienteDTO.getCpf(), clienteDTO.getEmail(), clienteDTO.getSenha(), clienteDTO.isAtivo());
            }
            Cliente savedCliente = clienteRepo.save(cliente);
            return criarClienteDTO(savedCliente);
        } catch (ParseException ex) {
            return null;
        }
    }

    public ClienteDTO buscarPorId(Long id) {
        Optional<Cliente> optCliente = clienteRepo.findById(id);
        Cliente cliente = optCliente.orElseThrow(() -> new RuntimeException("Não existe cliente com esse id"));
        return criarClienteDTO(cliente);
    }

    public List<Cliente> buscarPorNome(String nome) {
        List<Cliente> clientes = clienteRepo.findByNomeCompletoCustom(nome);
        return clientes;
    }

    public void deletarCliente(Long id) {
        clienteRepo.deleteById(id);
    }

    public void ativarDesativarCliente(boolean ativo, Long id) {
        clienteRepo.ativarUsuario(ativo, id);
    }

    private ClienteDTO criarClienteDTO(Cliente cliente) {
        DateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
        String dN = dtf.format(cliente.getDataNascimento());
        return ClienteDTO.builder().id(cliente.getId())
                .nomeCompleto(cliente.getNomeCompleto())
                .email(cliente.getEmail()).cpf(cliente.getCpf()).dataNascimento(dN)
                .senha(cliente.getSenha()).ativo(cliente.isAtivo()).build();
    }

}

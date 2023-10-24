package tech.ada.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.ada.ecommerce.dto.ClienteDTO;
import tech.ada.ecommerce.dto.ClienteEnderecoDTO;
import tech.ada.ecommerce.model.Cliente;
import tech.ada.ecommerce.model.ClienteEndereco;
import tech.ada.ecommerce.model.Endereco;
import tech.ada.ecommerce.model.Role;
import tech.ada.ecommerce.repository.ClienteEnderecoRepository;
import tech.ada.ecommerce.repository.ClienteRepository;
import tech.ada.ecommerce.repository.EnderecoRepository;
import tech.ada.ecommerce.repository.RoleRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

//    @Autowired
    ClienteRepository clienteRepo;
    RoleRepository roleRepo;
    EnderecoRepository enderecoRepo;
    ClienteEnderecoRepository clienteEnderecoRepo;
    PasswordEncoder passwordEncoder;

    public ClienteService(ClienteRepository clienteRepo, RoleRepository roleRepo, EnderecoRepository enderecoRepo,
                          ClienteEnderecoRepository clienteEnderecoRepo, PasswordEncoder passwordEncoder) {
        this.clienteRepo = clienteRepo;
        this.roleRepo = roleRepo;
        this.enderecoRepo = enderecoRepo;
        this.clienteEnderecoRepo = clienteEnderecoRepo;
        this.passwordEncoder = passwordEncoder;
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
            Cliente cliente = new Cliente(clienteDTO.getNomeCompleto(), dataNascimento,
                    clienteDTO.getCpf(), clienteDTO.getEmail(), passwordEncoder.encode(clienteDTO.getSenha()),
                    clienteDTO.isAtivo());
            Cliente savedCliente = clienteRepo.save(cliente);
            return criarClienteDTO(savedCliente);
        } catch (ParseException ex) {
            return null;
        }
    }

    public ClienteDTO atualizarCliente(ClienteDTO clienteDTO) {
        try {
            DateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
            Optional<Cliente> optCliente = clienteRepo.findById(clienteDTO.getId());
            Cliente cliente = optCliente.orElseThrow(() -> new RuntimeException("Não existe cliente com esse id"));
            if (clienteDTO.getEmail() != null && !clienteDTO.getEmail().isEmpty())
                cliente.setEmail(clienteDTO.getEmail());
            if (clienteDTO.getNomeCompleto() != null && !clienteDTO.getNomeCompleto().isEmpty())
                cliente.setNomeCompleto(clienteDTO.getNomeCompleto());
            if (clienteDTO.getDataNascimento() != null && !clienteDTO.getDataNascimento().isEmpty()) {
                cliente.setDataNascimento(dtf.parse(clienteDTO.getDataNascimento()));
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

    public ResponseEntity<String> alterarSenha(Long id, HashMap<String, String> senhas) {
        String senhaAtual = senhas.get("atual");
        String novaSenha = senhas.get("nova");
        String confirmarSenha = senhas.get("confirmar");
        Optional<Cliente> optCliente = clienteRepo.findById(id);
        Cliente cliente = optCliente.orElseThrow(() -> new RuntimeException("Não existe cliente com esse id"));
        if (passwordEncoder.matches(senhaAtual, cliente.getSenha())) {
            if (novaSenha.equals(confirmarSenha)) {
                cliente.setSenha(passwordEncoder.encode(novaSenha));
                clienteRepo.save(cliente);
                return new ResponseEntity<>("Senha atualizada com sucesso!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Nova senha e confirmar senha não são idênticas!",
                        HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } else {
            return new ResponseEntity<>("Senha atual inválida!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> adicionarRole(Long idCliente, Long idRole) {
        Optional<Cliente> optCliente = clienteRepo.findById(idCliente);
        Cliente cliente = optCliente.orElseThrow(() -> new RuntimeException("Não existe cliente com esse id"));
        Optional<Role> optRole = roleRepo.findById(idRole);
        Role role = optRole.orElseThrow(() -> new RuntimeException("Não existe role com esse id"));
        cliente.getRoles().add(role);
        clienteRepo.save(cliente);
        return new ResponseEntity<>("Role adicionada com sucesso!", HttpStatus.OK);
    }

    public void adicionarEndereco(ClienteEnderecoDTO clienteEnderecoDTO) {
        Optional<Cliente> optionalCliente = clienteRepo.findById(clienteEnderecoDTO.getClienteId());
        Cliente cliente = optionalCliente.orElseThrow(() -> new RuntimeException("Não existe cliente com esse id"));
        Optional<Endereco> optionalEndereco = enderecoRepo.findById(clienteEnderecoDTO.getEnderecoId());
        Endereco endereco = optionalEndereco.orElseThrow(() -> new RuntimeException("Não existe endereco com esse id"));
        ClienteEndereco clienteEndereco = new ClienteEndereco();
        clienteEndereco.setEndereco(endereco);
        clienteEndereco.setCliente(cliente);
        clienteEndereco.setTipo(clienteEnderecoDTO.getTipo());
        clienteEndereco.setNomeRecebedor(clienteEnderecoDTO.getNomeRecebedor());
        clienteEnderecoRepo.save(clienteEndereco);
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

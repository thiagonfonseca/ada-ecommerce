package tech.ada.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ada.ecommerce.model.Cliente;
import tech.ada.ecommerce.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class HelloWorldController {

    @Autowired
    ClienteService clienteService;

    @GetMapping("/hello")
    public String hello() {
        System.out.println("Hello World!");
        return "Olá Mundo";
    }

    @GetMapping("/{nome}")
    public List<Cliente> buscarClientes(@PathVariable("nome") String nome) {
        return clienteService.buscarPorNome(nome);
    }

}

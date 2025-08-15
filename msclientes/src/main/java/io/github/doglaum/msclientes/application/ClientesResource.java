package io.github.doglaum.msclientes.application;

import io.github.doglaum.msclientes.domain.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clientes")
public class ClientesResource {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String status() {
        return "ok";
    }

    @PostMapping
    public Cliente cadastrar(@RequestBody Cliente cliente) {
       return clienteService.save(cliente);
    }
}

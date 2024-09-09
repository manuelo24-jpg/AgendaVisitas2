package com.example.backend.controller;

import com.example.backend.models.Cliente;
import com.example.backend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "http://localhost:5173")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.findById(id);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cliente createCliente(@Validated @RequestBody Cliente cliente) {
        return clienteService.save(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id,
            @Validated @RequestBody Cliente clienteDetails) {
        Optional<Cliente> cliente = clienteService.findById(id);
        if (cliente.isPresent()) {
            Cliente updatedCliente = cliente.get();
            updatedCliente.setNombre(clienteDetails.getNombre());
            updatedCliente.setDireccion(clienteDetails.getDireccion());
            updatedCliente.setTelefono(clienteDetails.getTelefono());
            updatedCliente.setEmail(clienteDetails.getEmail());
            updatedCliente.setFechaRegistro(clienteDetails.getFechaRegistro());
            updatedCliente.setNotas(clienteDetails.getNotas());
            updatedCliente.setCiudad(clienteDetails.getCiudad());
            updatedCliente.setPais(clienteDetails.getPais());
            updatedCliente.setCodigoPostal(clienteDetails.getCodigoPostal());
            updatedCliente.setFechaNacimiento(clienteDetails.getFechaNacimiento());
            return ResponseEntity.ok(clienteService.save(updatedCliente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        if (clienteService.findById(id).isPresent()) {
            clienteService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
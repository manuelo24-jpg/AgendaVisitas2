package com.example.backend.controller;

import com.example.backend.models.Cliente;
import com.example.backend.models.Visita;
import com.example.backend.service.ClienteService;
import com.example.backend.service.VisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/visitas")
@CrossOrigin(origins = "http://localhost:5173")
public class VisitaController {

    @Autowired
    private VisitaService visitaService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Visita> getAllVisitas() {
        return visitaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visita> getVisitaById(@PathVariable Long id) {
        Optional<Visita> visita = visitaService.findById(id);
        return visita.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Visita> createVisita(@Validated @RequestBody Visita visita) {
        Optional<Cliente> cliente = clienteService.findById(visita.getClienteId());
        if (cliente.isPresent()) {
            visita.setCliente(cliente.get());
            Visita savedVisita = visitaService.save(visita);
            return ResponseEntity.ok(savedVisita);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Visita> updateVisita(@PathVariable Long id, @Validated @RequestBody Visita visitaDetails) {
        Optional<Visita> visita = visitaService.findById(id);
        if (visita.isPresent()) {
            Visita updatedVisita = visita.get();
            Optional<Cliente> cliente = clienteService.findById(visitaDetails.getClienteId());
            if (cliente.isPresent()) {
                updatedVisita.setCliente(cliente.get());
                updatedVisita.setFecha(visitaDetails.getFecha());
                updatedVisita.setHora(visitaDetails.getHora());
                updatedVisita.setNotas(visitaDetails.getNotas());
                updatedVisita.setDuracion(visitaDetails.getDuracion());
                updatedVisita.setTipoVisita(visitaDetails.getTipoVisita());
                return ResponseEntity.ok(visitaService.save(updatedVisita));
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisita(@PathVariable Long id) {
        if (visitaService.findById(id).isPresent()) {
            visitaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
package com.example.backend.controller;

import com.example.backend.models.Visita;
import com.example.backend.service.VisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/visitas")
public class VisitaController {

    @Autowired
    private VisitaService visitaService;

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
    public Visita createVisita(@Validated @RequestBody Visita visita) {
        return visitaService.save(visita);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Visita> updateVisita(@PathVariable Long id, @Validated @RequestBody Visita visitaDetails) {
        Optional<Visita> visita = visitaService.findById(id);
        if (visita.isPresent()) {
            Visita updatedVisita = visita.get();
            updatedVisita.setCliente(visitaDetails.getCliente());
            updatedVisita.setFecha(visitaDetails.getFecha());
            updatedVisita.setHora(visitaDetails.getHora());
            updatedVisita.setDescripcion(visitaDetails.getDescripcion());
            updatedVisita.setDuracion(visitaDetails.getDuracion());
            updatedVisita.setTipoVisita(visitaDetails.getTipoVisita());
            updatedVisita.setEstado(visitaDetails.getEstado());
            return ResponseEntity.ok(visitaService.save(updatedVisita));
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
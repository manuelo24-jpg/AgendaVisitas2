package com.example.backend.service;

import com.example.backend.models.Visita;
import com.example.backend.repository.VisitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitaService {

    @Autowired
    private VisitaRepository visitaRepository;

    public List<Visita> findAll() {
        return visitaRepository.findAll();
    }

    public Optional<Visita> findById(Long id) {
        return visitaRepository.findById(id);
    }

    public Visita save(Visita visita) {
        return visitaRepository.save(visita);
    }

    public void deleteById(Long id) {
        visitaRepository.deleteById(id);
    }
}
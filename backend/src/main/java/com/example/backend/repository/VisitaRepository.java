package com.example.backend.repository;

import com.example.backend.models.Visita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitaRepository extends JpaRepository<Visita, Long> {
}

package com.example.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;
import java.util.Date;

@Entity
public class Visita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum TipoVisita {
        CONSULTA,
        REVISION,
        SEGUIMIENTO,
        OTRO;
    }

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnore
    private Cliente cliente;

    @Transient
    @JsonProperty("clienteId")
    private Long clienteId;

    @NotNull(message = "La fecha es obligatoria")
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fecha;

    @NotNull(message = "La hora es obligatoria")
    @JsonFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime hora;

    @Lob
    private String notas;

    @Column(nullable = false)
    private Integer duracion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVisita tipoVisita;

    @JsonProperty("clienteNombre")
    public String getClienteNombre() {
        return cliente != null ? cliente.getNombre() : null;
    }

    public Long getClienteId() {
        return cliente != null ? cliente.getId() : clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public TipoVisita getTipoVisita() {
        return tipoVisita;
    }

    public void setTipoVisita(TipoVisita tipoVisita) {
        this.tipoVisita = tipoVisita;
    }
}
package com.example.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    @NotBlank(message = "La fecha es obligatoria")
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fecha;

    @NotBlank(message = "La hora es obligatoria")
    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    private Date hora;

    @Lob
    private String notas;

    @Column(nullable = false)
    private Integer duracion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVisita tipoVisita;

    @JsonProperty("clienteNombre")
    public String getClienteNombre() {
        return cliente.getNombre();
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

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
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
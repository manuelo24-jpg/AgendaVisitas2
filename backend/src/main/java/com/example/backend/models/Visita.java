package com.example.backend.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Visita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fecha;

    @Temporal(TemporalType.TIME)
    @Column(nullable = false)
    private Date hora;

    @Lob
    private String descripcion;

    @Column(nullable = false)
    private Integer duracion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVisita tipoVisita;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;

    public Visita() {
    }

    public Visita(Cliente cliente, Date fecha, Date hora, String descripcion, Integer duracion, TipoVisita tipoVisita,
            Estado estado) {
        this.cliente = cliente;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.tipoVisita = tipoVisita;
        this.estado = estado;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

}

enum TipoVisita {
    CONSULTA, REVISION, SEGUIMIENTO, OTRO
}

enum Estado {
    COMPLETADA, PENDIENTE, CANCELADA
}

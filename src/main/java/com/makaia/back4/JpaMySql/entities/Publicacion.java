package com.makaia.back4.JpaMySql.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column
    private String titulo;

    @Column
    private String contenido;

    @ManyToOne(optional = false)
    Usuario usuario;

    @OneToMany(mappedBy = "publicacion")
    List<Comentario> comentarios;

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }
}

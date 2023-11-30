package com.makaia.back4.JpaMySql.dtos;

public class CrearPublicacionDTO {
    private String titulo;

    private String contenido;

    private Long usuarioId;

    public CrearPublicacionDTO() {
    }

    public CrearPublicacionDTO(String titulo, String contenido, Long usuarioId) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.usuarioId = usuarioId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}

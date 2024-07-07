package com.aluracursos.literalura.modelos;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> idiomas;
    private Double descargas;
    private String nombreAutor;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libros(){

    }
    public Libros(DatosLibros datosLibro, Autor autor){
        this.titulo = datosLibro.titulo();
        this.nombreAutor = datosLibro.autor().stream()
                .map(DatosAutor::autor).collect(Collectors.toList())
                .toString();
        this.idiomas = datosLibro.idiomas();
        this.descargas = datosLibro.totalDescargas();
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Double getDescargas() {
        return descargas;
    }

    public void setDescargas(Double descargas) {
        this.descargas = descargas;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
        if(autor != null &&  !autor.getLibros().contains(this)){
            autor.getLibros().add(this);
        }
    }

    @Override
    public String toString() {
        return """
                
                * ********************== FICHA DEL LIBRO ==*************************
                *
                *    Nombre del libro: %s
                *    El autor: %s
                *    El idioma: %s
                *    Total de descargas: %s
                *
                **********************************************************************
                """.formatted(this.titulo, this.autor.getNombre(), this.idiomas.get(0), this.descargas);
    }
}

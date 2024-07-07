package com.aluracursos.literalura.modelos;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private String fechaNacimiento;
    private String fechaMuerte;
    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Libros> libros = new HashSet<>();

    public Autor(){
    }

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.autor();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaMuerte = datosAutor.fechaMuerte();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaMuerte() {
        return fechaMuerte;
    }

    public void setFechaMuerte(String fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }

    public Set<Libros> getLibros() {
        return libros;
    }

    public void setLibros(Set<Libros> libros) {
        this.libros = libros;
        for(Libros libro : libros){
            libro.setAutor(this);
        }
    }

    @Override
    public String toString(){
        return
                "********************************************\n*\n* " +
                "Autor: " + this.nombre +
                        "\n* Nació: " + this.fechaNacimiento +
                        "\n* Murió: " + this.fechaMuerte +
                        "\n* Libros:[" + this.getLibros().stream()
                        .map(Libros::getTitulo)
                        .collect(Collectors.joining(", ")) + "]" +
                        "\n*\n*\n********************************************\n ";
    }
}

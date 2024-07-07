package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.modelos.Autor;
import com.aluracursos.literalura.modelos.DatosAutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNombre(String autor);

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :anoBusqueda AND a.fechaMuerte >= :anoBusqueda ")
    List<Autor> autoresVivosEnAno(int anoBusqueda);
}

package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.modelos.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibrosRepository extends JpaRepository<Libros, Long> {

    Libros findByTituloContainsIgnoreCase(String titulo);

    List<Libros> findByIdiomas(String idiomaSeleccionado);
}

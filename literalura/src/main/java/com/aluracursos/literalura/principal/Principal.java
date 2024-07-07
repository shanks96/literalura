package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.modelos.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibrosRepository;
import com.aluracursos.literalura.servicios.ConsumoAPI;
import com.aluracursos.literalura.servicios.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    //Variables
    private Scanner teclado = new Scanner(System.in);
    private String URL_BASE = "https://gutendex.com/books";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private AutorRepository autorRepository;
    private LibrosRepository librosRepository;
    private List<DatosLibros> datosLibros = new ArrayList<>();
    private List<Libros> libros;
    private List<Autor> autor;

    //Se crear las entidades del repository
    public Principal(){}
    public Principal(LibrosRepository librosRepository, AutorRepository autorRepository){
        this.librosRepository = librosRepository;
        this.autorRepository = autorRepository;
    }


    //Menú principal
    public void muestraMenu(){
        var opcion = 1;
        while(opcion != 0){
            System.out.println("""
                    ##############################################
                    
                    Selecciona una opción:
                    
                    1. Buscar libros por título en gutendex
                    2. Mostrar libros registrados
                    3. Mostrar autores registrados
                    4. Mostrar autores vivos en determinado año
                    5. Mostrar libros por idiomas
                    
                    0. Salir
                    
                    ###############################################
                    
                    """);
            opcion = Integer.parseInt(teclado.nextLine());
            if(opcion !=  0){
                ejecutarOpcion(opcion);
            }
        }


    }

    private void ejecutarOpcion(int opcion) {
        switch (opcion){
            case 1:
                System.out.println("Buscando libro en gutendex...");
                buscarLibro();
                break;
            case 2:
                System.out.println("Listando libros en base de datos...");
                listarLibros();
                break;
            case 3:
                System.out.println("Listando autores en base de datos...");
                listarAutores();
                break;
            case 4:
                System.out.println("Autores vivos por año...");
                autoresPorAno();
                break;
            case 5:
                System.out.println("Libros por idioma...");
                librosPorIdioma();
                break;
            default:
                System.out.println("Opción no válida, intente de nuevo...");
                break;
        }

    }

    private void librosPorIdioma() {
        System.out.println("""
                #_______________________________________________
                #
                #   Digita el idioma que quieres buscar:
                #
                #   es - Español
                #   en - Inglés
                #   fr - Francés
                #   pt - Portugués
                #_______________________________________________
                """);
        var idiomaSeleccionado = teclado.nextLine();
//        try{
            libros = librosRepository.findByIdiomas(idiomaSeleccionado);
            if(libros == null){
                System.out.println("No hay libros en ese idioma");
            }else{
                libros.forEach(System.out::println);
            }
//        }

    }

    private void autoresPorAno() {
        System.out.println("Ingrese el año de busqueda: ");
        var anoBusqueda = Integer.parseInt(teclado.nextLine());
        autor = autorRepository.autoresVivosEnAno(anoBusqueda);
        if (autor == null){
            System.out.println("No hay autores vivos en ese año en nuestra Base de Datos.");
        }else{
            System.out.println("###############################################\n#");
            System.out.println("#       Autores vivos en " + anoBusqueda);
            System.out.println("#\n###############################################");
            autor.forEach(System.out::println);
        }

    }

    private void listarAutores() {
        autor = autorRepository.findAll();
        autor.forEach(System.out::println);
    }

    private void listarLibros() {
        libros = librosRepository.findAll();
        libros.forEach(System.out::println);
    }

    private void buscarLibro() {
        System.out.println("¿Qué libro desea buscar?");
        String libroBuscado = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + libroBuscado.toLowerCase().replace(" ", "+"));
        DatosGenerales resultado = convierteDatos.obtenerDatos(json, DatosGenerales.class);

        Optional<DatosLibros> libroEncontrado = resultado.resultado().stream()
                .filter(l -> l.titulo().toUpperCase().contains(libroBuscado.toUpperCase()))
                .findFirst();

        if (libroEncontrado.isPresent()){
            DatosLibros datosLibros = libroEncontrado.get();
            DatosAutor datosAutor = datosLibros.autor().get(0);

            Autor autor = null;
//            System.out.println(autor.getNombre());

            if(autorRepository.findByNombre(datosAutor.autor()) == null){
                Autor autornuevo = new Autor(datosAutor);
                autorRepository.save(autornuevo);
                autor = autornuevo;
            }else{
                autor = autorRepository.findByNombre(datosAutor.autor());
            }

            Libros libro = null;

            if(librosRepository.findByTituloContainsIgnoreCase(datosLibros.titulo()) == null){
                System.out.println("Se encontro el libro " + libroBuscado.toUpperCase());
                Libros libroNuevo = new Libros(datosLibros, autor);
                System.out.println(libroNuevo);
                librosRepository.save(libroNuevo);
                libro = libroNuevo;


            } else {
                System.out.println("Libro ya Registrado");
                libro = librosRepository.findByTituloContainsIgnoreCase(datosLibros.titulo());
                System.out.println(libro);
            }
        }else {
            System.out.println("Libro no encontrado");
        }


    }


}

package com.aluracursos.literalura.servicios;

public interface iConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}

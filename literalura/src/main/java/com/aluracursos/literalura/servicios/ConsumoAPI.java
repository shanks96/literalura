package com.aluracursos.literalura.servicios;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;

public class ConsumoAPI {
    public String obtenerDatos(String url){
        System.out.println(url);
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS).build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = null;

        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            System.out.println("estoy en el 1er catch");
            throw new RuntimeException(e);

        } catch(InterruptedException e) {
            System.out.println("estoy en el 2do catch");
            throw new RuntimeException(e);

        }

        return response.body();
    }
}

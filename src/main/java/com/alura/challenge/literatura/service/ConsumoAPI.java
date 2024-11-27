package com.alura.challenge.literatura.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConsumoAPI {

    // Método para obtener los datos de la API en formato JSON
    public String obtenerDatos(String urlString) {
        StringBuilder resultado = new StringBuilder();
        HttpURLConnection conexion = null;

        try {
            // Crear la URL
            URL url = new URL(urlString);

            // Abrir la conexión
            conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");  // Usamos el método GET
            conexion.setConnectTimeout(18000);  // Tiempo de espera para la conexión
            conexion.setReadTimeout(18000);     // Tiempo de espera para leer la respuesta

            // Leer la respuesta de la API
            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                resultado.append(inputLine);  // Agregar la respuesta al StringBuilder
            }
            in.close();  // Cerrar el BufferedReader

        } catch (Exception e) {
            System.out.println("Error al hacer la solicitud HTTP: " + e.getMessage());
            return null;  // Si hay un error, retornamos null
        } finally {
            if (conexion != null) {
                conexion.disconnect();  // Aseguramos de cerrar la conexión
            }
        }

        return resultado.toString();  // Retornamos el JSON como una cadena
    }
}


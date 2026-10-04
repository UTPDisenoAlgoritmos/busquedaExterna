package pe.edu.utp.busqueda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BusquedaPorBloques {
    private static final int TAMANIO_BLOQUE = 4;

    public static ResultadoBusqueda buscar(String nombreArchivo, String codigoBuscado) throws IOException {
        Metricas metricas = new Metricas();
        metricas.iniciarTiempo();
        String registroEncontrado = null;
        int registroEnBloque = 0;
        metricas.incrementarAccesosArchivo();

        try (BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (registroEnBloque == 0) {
                    metricas.incrementarBloquesLeidos();
                }

                String[] datos = linea.split(",");
                String codigo = datos[0];
                metricas.incrementarComparaciones();

                if (codigo.equalsIgnoreCase(codigoBuscado)) {
                    registroEncontrado = linea;
                    break;
                }

                registroEnBloque++;
                if (registroEnBloque == TAMANIO_BLOQUE) {
                    registroEnBloque = 0;
                }
            }
        }

        metricas.detenerTiempo();
        return new ResultadoBusqueda("Secuencial mediante bloques", registroEncontrado,
                registroEncontrado != null, metricas);
    }
}

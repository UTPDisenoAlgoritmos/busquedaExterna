package pe.edu.utp.busqueda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.PrintWriter;

public class BusquedaConIndice {

    public static void crearIndice(String archivoDatos, String archivoIndice) throws IOException {
        try (RandomAccessFile datos = new RandomAccessFile(archivoDatos, "r");
             PrintWriter indice = new PrintWriter(archivoIndice)) {

            while (true) {
                long posicion = datos.getFilePointer();
                String linea = datos.readLine();
                if (linea == null) break;

                String codigo = linea.split(",")[0];
                indice.println(codigo + "," + posicion);
            }
        }
    }

    public static ResultadoBusqueda buscar(String archivoDatos, String archivoIndice,
                                             String codigoBuscado) throws IOException {
        Metricas metricas = new Metricas();
        metricas.iniciarTiempo();
        Long posicionEncontrada = null;

        metricas.incrementarAccesosIndice();
        try (BufferedReader lectorIndice = new BufferedReader(new FileReader(archivoIndice))) {
            String lineaIndice;
            while ((lineaIndice = lectorIndice.readLine()) != null) {
                String[] partes = lineaIndice.split(",");
                String codigo = partes[0];
                metricas.incrementarComparaciones();

                if (codigo.equalsIgnoreCase(codigoBuscado)) {
                    posicionEncontrada = Long.parseLong(partes[1]);
                    break;
                }
            }
        }

        String registroEncontrado = null;
        if (posicionEncontrada != null) {
            metricas.incrementarAccesosArchivo();
            metricas.incrementarBloquesLeidos();

            try (RandomAccessFile datos = new RandomAccessFile(archivoDatos, "r")) {
                datos.seek(posicionEncontrada);
                registroEncontrado = datos.readLine();
            }
        }

        metricas.detenerTiempo();
        return new ResultadoBusqueda("Secuencial con indice", registroEncontrado,
                registroEncontrado != null, metricas);
    }
}

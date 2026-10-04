package pe.edu.utp.busqueda;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BusquedaPorHashExterno {
    private static final int NUM_BUCKETS = 10;

    public static int funcionHash(String codigo) {
        int numero = Integer.parseInt(codigo.replaceAll("[^0-9]", ""));
        return numero % NUM_BUCKETS;
    }

    public static int crearBuckets(String archivoDatos, String carpetaBuckets) throws IOException {
        File carpeta = new File(carpetaBuckets);
        if (!carpeta.exists()) carpeta.mkdirs();

        for (int i = 0; i < NUM_BUCKETS; i++) {
            File bucket = new File(carpeta, "bucket_" + i + ".txt");
            try (PrintWriter pw = new PrintWriter(bucket)) {
                // limpiar archivo
            }
        }

        int[] elementosPorBucket = new int[NUM_BUCKETS];
        int colisiones = 0;

        try (BufferedReader lector = new BufferedReader(new FileReader(archivoDatos))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String codigo = linea.split(",")[0];
                int bucket = funcionHash(codigo);

                if (elementosPorBucket[bucket] > 0) colisiones++;

                File archivoBucket = new File(carpeta, "bucket_" + bucket + ".txt");
                try (PrintWriter salida = new PrintWriter(new FileWriter(archivoBucket, true))) {
                    salida.println(linea);
                }
                elementosPorBucket[bucket]++;
            }
        }
        return colisiones;
    }

    public static ResultadoBusqueda buscar(String carpetaBuckets, String codigoBuscado,
                                             int colisionesConstruccion) throws IOException {
        Metricas metricas = new Metricas();
        metricas.iniciarTiempo();

        int bucket = funcionHash(codigoBuscado);
        File archivoBucket = new File(carpetaBuckets, "bucket_" + bucket + ".txt");

        metricas.incrementarAccesosArchivo();
        metricas.incrementarBloquesLeidos();
        for (int i = 0; i < colisionesConstruccion; i++) metricas.incrementarColisiones();

        String registroEncontrado = null;
        try (BufferedReader lector = new BufferedReader(new FileReader(archivoBucket))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String codigo = linea.split(",")[0];
                metricas.incrementarComparaciones();

                if (codigo.equalsIgnoreCase(codigoBuscado)) {
                    registroEncontrado = linea;
                    break;
                }
            }
        }

        metricas.detenerTiempo();
        return new ResultadoBusqueda("Transformacion de claves / Hash externo",
                registroEncontrado, registroEncontrado != null, metricas);
    }
}

package pe.edu.utp.busqueda;

import java.io.IOException;
import java.util.Scanner;

public class App {
    private static final String ARCHIVO_DATOS = "data/productos.txt";
    private static final String ARCHIVO_INDICE = "data/indice.txt";
    private static final String CARPETA_BUCKETS = "data/buckets";

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        try {
            BusquedaConIndice.crearIndice(ARCHIVO_DATOS, ARCHIVO_INDICE);
            int colisionesHash = BusquedaPorHashExterno.crearBuckets(ARCHIVO_DATOS, CARPETA_BUCKETS);

            System.out.println("============================================");
            System.out.println(" SEMANA 08 - BUSQUEDA EXTERNA");
            System.out.println(" Caso: Inventario TecnoStore");
            System.out.println("============================================");
            System.out.print("Codigo a buscar (ejemplo P108): ");
            String codigo = teclado.nextLine().trim();

            ResultadoBusqueda porBloques = BusquedaPorBloques.buscar(ARCHIVO_DATOS, codigo);
            ResultadoBusqueda porIndice = BusquedaConIndice.buscar(ARCHIVO_DATOS, ARCHIVO_INDICE, codigo);
            ResultadoBusqueda porHash = BusquedaPorHashExterno.buscar(CARPETA_BUCKETS, codigo, colisionesHash);

            mostrarResultado(porBloques);
            mostrarResultado(porIndice);
            mostrarResultado(porHash);
            ComparadorMetricas.mostrar(porBloques, porIndice, porHash);

        } catch (IOException e) {
            System.out.println("Error al trabajar con los archivos: " + e.getMessage());
        } finally {
            teclado.close();
        }
    }

    private static void mostrarResultado(ResultadoBusqueda resultado) {
        System.out.println();
        System.out.println("============================================");
        System.out.println(resultado.algoritmo());
        System.out.println("============================================");
        if (resultado.encontrado()) {
            System.out.println("Encontrado: " + resultado.registro());
        } else {
            System.out.println("Registro no encontrado.");
        }
        resultado.metricas().mostrar(resultado.algoritmo());
    }
}

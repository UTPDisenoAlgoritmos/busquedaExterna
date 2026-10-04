package pe.edu.utp.busqueda;

public class ComparadorMetricas {
    public static void mostrar(ResultadoBusqueda a, ResultadoBusqueda b, ResultadoBusqueda c) {
        System.out.println();
        System.out.println("====================================================================================");
        System.out.println("                         COMPARACION DE METRICAS");
        System.out.println("====================================================================================");
        System.out.printf("%-38s %10s %10s %10s %10s %12s%n",
                "Algoritmo", "Comparac.", "Bloques", "Acc.Datos", "Colision.", "Tiempo ms");
        System.out.println("------------------------------------------------------------------------------------");
        imprimirFila(a);
        imprimirFila(b);
        imprimirFila(c);
        System.out.println("====================================================================================");
        System.out.println("Nota: con archivos pequenos el tiempo puede variar entre ejecuciones.");
        System.out.println("Para busqueda externa, compare sobre todo bloques y accesos al archivo.");
    }

    private static void imprimirFila(ResultadoBusqueda r) {
        Metricas m = r.metricas();
        System.out.printf("%-38s %10d %10d %10d %10d %12.6f%n",
                r.algoritmo(), m.getComparaciones(), m.getBloquesLeidos(),
                m.getAccesosArchivo(), m.getColisiones(), m.getTiempoMs());
    }
}

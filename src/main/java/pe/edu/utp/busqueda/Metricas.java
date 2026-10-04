package pe.edu.utp.busqueda;

public class Metricas {
    private long inicioNs;
    private long finNs;
    private int comparaciones;
    private int bloquesLeidos;
    private int accesosArchivo;
    private int accesosIndice;
    private int colisiones;

    public void iniciarTiempo() { inicioNs = System.nanoTime(); }
    public void detenerTiempo() { finNs = System.nanoTime(); }
    public void incrementarComparaciones() { comparaciones++; }
    public void incrementarBloquesLeidos() { bloquesLeidos++; }
    public void incrementarAccesosArchivo() { accesosArchivo++; }
    public void incrementarAccesosIndice() { accesosIndice++; }
    public void incrementarColisiones() { colisiones++; }

    public int getComparaciones() { return comparaciones; }
    public int getBloquesLeidos() { return bloquesLeidos; }
    public int getAccesosArchivo() { return accesosArchivo; }
    public int getAccesosIndice() { return accesosIndice; }
    public int getColisiones() { return colisiones; }
    public double getTiempoMs() { return (finNs - inicioNs) / 1_000_000.0; }

    public void mostrar(String algoritmo) {
        System.out.println("\n--- METRICAS: " + algoritmo + " ---");
        System.out.println("Comparaciones     : " + comparaciones);
        System.out.println("Bloques leidos    : " + bloquesLeidos);
        System.out.println("Accesos archivo   : " + accesosArchivo);
        System.out.println("Accesos indice    : " + accesosIndice);
        System.out.println("Colisiones        : " + colisiones);
        System.out.printf("Tiempo (ms)       : %.6f%n", getTiempoMs());
    }
}

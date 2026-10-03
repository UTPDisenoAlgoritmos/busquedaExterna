package pe.edu.utp.busqueda;

public record ResultadoBusqueda(
        String algoritmo,
        String registro,
        boolean encontrado,
        Metricas metricas) {
}

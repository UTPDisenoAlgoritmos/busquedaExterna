package pe.edu.utp.busqueda;

import java.io.IOException;
import java.util.Scanner;

public class App {
	private static final String ARCHIVO_DATOS = "data/productos.txt";
	private static final String ARCHIVO_INDICE = "data/indice.txt";
	private static final String CARPETA_BUCKETS = "data/buckets";

	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);

		System.out.println("============================================");
		System.out.println(" SEMANA 08 - BUSQUEDA EXTERNA");
		System.out.println(" Caso: Inventario TecnoStore");
		System.out.println("============================================");
		System.out.print("Codigo a buscar (ejemplo P108): ");
		String codigo = teclado.nextLine().trim();

		teclado.close();

	}

}

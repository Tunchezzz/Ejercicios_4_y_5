package vista;

import java.util.Scanner;
import java.util.Locale;

public class Vista {
    private Scanner entrada;

    public Vista() {
        entrada = new Scanner(System.in);
    }

    public void mostrar(String mensaje) { System.out.println(mensaje); }

    public String leerTexto(String mensaje) {
        mostrar(mensaje);
        return entrada.nextLine().trim();
    }

    public int leerEntero(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            Scanner numero = new Scanner(texto);
            if (numero.hasNextInt()) {
                int valor = numero.nextInt();
                if (!numero.hasNext()) { return valor; }
            }
            mostrar("Escriba un numero entero.");
        }
    }

    public double leerDecimal(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            Scanner numero = new Scanner(texto);
            numero.useLocale(Locale.US);
            if (numero.hasNextDouble()) {
                double valor = numero.nextDouble();
                if (!numero.hasNext() && Double.isFinite(valor)) { return valor; }
            }
            mostrar("Escriba un numero. Use punto para los decimales.");
        }
    }

    public boolean leerSiNo(String mensaje) {
        String respuesta = leerTexto(mensaje + " (s/n)");
        while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
            respuesta = leerTexto("Escriba s o n.");
        }
        return respuesta.equalsIgnoreCase("s");
    }

    public int menu() {
        mostrar("\nRENTAMOVIL\n1. Registrar vehiculo\n2. Registrar cliente\n3. Consultar flota"
                + "\n4. Consultar clientes\n5. Cotizar\n6. Alquilar y confirmar o cancelar"
                + "\n7. Registrar devolucion\n8. Terminar mantenimiento\n9. Reporte por categoria"
                + "\n10. Alquileres activos\n11. Historial de cliente\n0. Salir");
        return leerEntero("Seleccione una opcion:");
    }
}

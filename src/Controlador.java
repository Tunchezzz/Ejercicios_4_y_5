package controlador;

import modelo.*;
import vista.Vista;

public class Controlador {
    private Empresa empresa;
    private Vista vista;

    public Controlador(Empresa empresa, Vista vista) {
        this.empresa = empresa;
        this.vista = vista;
    }

    public void iniciar() {
        int opcion;
        do {
            opcion = vista.menu();
            switch (opcion) {
                case 1: registrarVehiculo(); break;
                case 2: registrarCliente(); break;
                case 3: vista.mostrar(empresa.consultarFlota()); break;
                case 4: vista.mostrar(empresa.consultarClientes()); break;
                case 5: solicitarAlquiler(false); break;
                case 6: solicitarAlquiler(true); break;
                case 7: vista.mostrar(empresa.devolverVehiculo(vista.leerTexto("Placa:"))); break;
                case 8: vista.mostrar(empresa.terminarMantenimiento(vista.leerTexto("Placa:"))); break;
                case 9: vista.mostrar(empresa.reporteCategorias()); break;
                case 10: vista.mostrar(empresa.reporteActivos()); break;
                case 11: vista.mostrar(empresa.historialCliente(vista.leerTexto("Identificador del cliente:"))); break;
                case 0: vista.mostrar("Programa finalizado."); break;
                default: vista.mostrar("Opcion incorrecta.");
            }
        } while (opcion != 0);
    }

    private void registrarVehiculo() {
        int categoria = vista.leerEntero("1. Automovil\n2. Motocicleta\n3. Camioneta de carga\n4. Microbus");
        if (categoria < 1 || categoria > 4) { vista.mostrar("Categoria incorrecta."); return; }
        String placa = vista.leerTexto("Placa:");
        String marca = vista.leerTexto("Marca:");
        String modelo = vista.leerTexto("Modelo:");
        double tarifa = vista.leerDecimal("Tarifa diaria:");
        Vehiculo vehiculo = null;
        switch (categoria) {
            case 1:
                vehiculo = new Automovil(placa, marca, modelo, tarifa, 0,
                        vista.leerEntero("Pasajeros:"), vista.leerSiNo("Transmision automatica"));
                break;
            case 2:
                vehiculo = new Motocicleta(placa, marca, modelo, tarifa, 0, vista.leerEntero("Cilindraje:"));
                break;
            case 3:
                vehiculo = new CamionetaCarga(placa, marca, modelo, tarifa, 0, vista.leerDecimal("Capacidad en toneladas:"));
                break;
            case 4:
                vehiculo = new Microbus(placa, marca, modelo, tarifa, 0,
                        vista.leerEntero("Pasajeros:"), vista.leerSiNo("Incluye piloto"));
                break;
        }
        vista.mostrar(empresa.registrarVehiculo(vehiculo));
    }

    private void registrarCliente() {
        int tipo = vista.leerEntero("1. Individual\n2. Corporativo");
        if (tipo < 1 || tipo > 2) { vista.mostrar("Tipo incorrecto."); return; }
        String identificador = vista.leerTexto("DPI o NIT:");
        String nombre = vista.leerTexto("Nombre:");
        String licencias = vista.leerTexto("Licencias A, B, C o M juntas, sin espacios. Ejemplo: BM");
        Cliente cliente;
        if (tipo == 1) {
            cliente = new ClienteIndividual(identificador, nombre, licencias, 0);
        } else {
            cliente = new ClienteCorporativo(identificador, nombre, licencias,
                    vista.leerTexto("Nombre de la empresa:"), vista.leerTexto("Contacto:"));
        }
        vista.mostrar(empresa.registrarCliente(cliente));
    }

    private void solicitarAlquiler(boolean confirmar) {
        String placa = vista.leerTexto("Placa:");
        String identificador = vista.leerTexto("Identificador del cliente:");
        int dias = vista.leerEntero("Dias de alquiler:");
        vista.mostrar(empresa.cotizar(placa, identificador, dias));
        if (!confirmar) { return; }
        if (!empresa.validarSolicitud(placa, identificador, dias).isEmpty()) { return; }
        if (!empresa.motivosRechazo(empresa.buscarVehiculo(placa), empresa.buscarCliente(identificador)).isEmpty()) { return; }
        if (vista.leerSiNo("Confirmar alquiler")) {
            vista.mostrar(empresa.confirmarAlquiler(placa, identificador, dias));
        } else {
            vista.mostrar("Alquiler cancelado. No se realizaron cambios.");
        }
    }
}

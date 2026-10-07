package modelo;

import java.util.ArrayList;

public class Empresa {
    private ArrayList<Vehiculo> flota;
    private ArrayList<Cliente> clientes;
    private ArrayList<Alquiler> alquileres;

    public Empresa() {
        flota = new ArrayList<Vehiculo>();
        clientes = new ArrayList<Cliente>();
        alquileres = new ArrayList<Alquiler>();
    }

    public void cargarDatosIniciales() {
        if (!flota.isEmpty() || !clientes.isEmpty()) { return; }
        registrarVehiculo(new Automovil("P001AAA", "Toyota", "Corolla", 200, 28, 5, true));
        registrarVehiculo(new Automovil("P002AAA", "Kia", "Rio", 150, 0, 5, false));
        registrarVehiculo(new Motocicleta("M001AAA", "Honda", "CB250", 100, 18, 250));
        registrarVehiculo(new Motocicleta("M002AAA", "Yamaha", "MT03", 120, 0, 321));
        registrarVehiculo(new CamionetaCarga("C001AAA", "Toyota", "Hilux", 200, 13, 1.5));
        registrarVehiculo(new CamionetaCarga("C002AAA", "Isuzu", "DMax", 250, 0, 2));
        registrarVehiculo(new Microbus("B001AAA", "Toyota", "Hiace", 450, 23, 15, true));
        registrarVehiculo(new Microbus("B002AAA", "Hyundai", "H1", 350, 0, 12, false));
        registrarCliente(new ClienteIndividual("1234567890101", "Ana Lopez", "CM", 0));
        registrarCliente(new ClienteIndividual("1234567890102", "Luis Perez", "A", 3));
        registrarCliente(new ClienteCorporativo("1001-1", "Transportes Norte", "ABM", "Transportes Norte", "Maria Ruiz"));
        registrarCliente(new ClienteCorporativo("1002-2", "Comercial Sur", "C", "Comercial Sur", "Pedro Diaz"));
    }

    public Vehiculo buscarVehiculo(String placa) {
        for (Vehiculo vehiculo : flota) {
            if (vehiculo.getPlaca().equalsIgnoreCase(placa.trim())) { return vehiculo; }
        }
        return null;
    }

    public Cliente buscarCliente(String identificador) {
        for (Cliente cliente : clientes) {
            if (cliente.getIdentificador().equalsIgnoreCase(identificador.trim())) { return cliente; }
        }
        return null;
    }

    public String registrarVehiculo(Vehiculo vehiculo) {
        if (vehiculo.getPlaca().isEmpty() || buscarVehiculo(vehiculo.getPlaca()) != null) {
            return "Placa vacia o repetida.";
        }
        if (vehiculo.getTarifaDiaria() <= 0 || !Double.isFinite(vehiculo.getTarifaDiaria())
                || vehiculo.getDiasAcumulados() < 0 || !vehiculo.datosValidos()) {
            return "Los datos numericos del vehiculo no son validos.";
        }
        flota.add(vehiculo);
        return "Vehiculo registrado.";
    }

    public String registrarCliente(Cliente cliente) {
        if (cliente.getIdentificador().isEmpty() || buscarCliente(cliente.getIdentificador()) != null) {
            return "Identificador vacio o repetido.";
        }
        if (!cliente.licenciasValidas() || !cliente.datosValidos() || cliente.getAlquileresConfirmados() < 0) {
            return "DPI, licencias o datos del cliente incorrectos.";
        }
        clientes.add(cliente);
        return "Cliente registrado.";
    }

    public int contarActivos(Cliente cliente) {
        int cantidad = 0;
        for (Alquiler alquiler : alquileres) {
            if (alquiler.isActivo() && alquiler.getCliente() == cliente) { cantidad++; }
        }
        return cantidad;
    }

    public String validarSolicitud(String placa, String identificador, int dias) {
        String errores = "";
        if (buscarVehiculo(placa) == null) { errores += "Placa inexistente.\n"; }
        if (buscarCliente(identificador) == null) { errores += "Cliente inexistente.\n"; }
        if (dias <= 0) { errores += "Los dias deben ser enteros positivos.\n"; }
        return errores;
    }

    public String motivosRechazo(Vehiculo vehiculo, Cliente cliente) {
        String motivos = "";
        if (!vehiculo.getEstado().equals("Disponible")) { motivos += "Vehiculo no disponible.\n"; }
        if (!cliente.tieneLicencia(vehiculo.getLicenciaRequerida())) {
            motivos += "Licencia inadecuada. Se requiere: " + vehiculo.getLicenciaRequerida() + ".\n";
        }
        if (contarActivos(cliente) >= cliente.getLimiteActivos()) {
            motivos += "Limite de alquileres activos alcanzado.\n";
        }
        return motivos;
    }

    public String cotizar(String placa, String identificador, int dias) {
        String errores = validarSolicitud(placa, identificador, dias);
        if (!errores.isEmpty()) { return errores; }
        Vehiculo vehiculo = buscarVehiculo(placa);
        Cliente cliente = buscarCliente(identificador);
        double subtotal = Math.round(vehiculo.calcularSubtotal(dias) * 100.0) / 100.0;
        double descuento = Math.round(subtotal * cliente.getPorcentajeDescuento() * 100.0) / 100.0;
        String motivos = motivosRechazo(vehiculo, cliente);
        String resultado = vehiculo.getDescripcion()
                + String.format("\nDias: %d | Subtotal: Q%.2f | Descuento: Q%.2f | Total: Q%.2f\n",
                dias, subtotal, descuento, subtotal - descuento);
        if (motivos.isEmpty()) { return resultado + "El cliente puede alquilarlo."; }
        return resultado + "No puede alquilarlo:\n" + motivos;
    }

    public String confirmarAlquiler(String placa, String identificador, int dias) {
        String errores = validarSolicitud(placa, identificador, dias);
        if (!errores.isEmpty()) { return errores; }
        Vehiculo vehiculo = buscarVehiculo(placa);
        Cliente cliente = buscarCliente(identificador);
        String motivos = motivosRechazo(vehiculo, cliente);
        if (!motivos.isEmpty()) { return motivos; }
        Alquiler alquiler = new Alquiler(alquileres.size() + 1, cliente, vehiculo, dias);
        alquileres.add(alquiler);
        vehiculo.alquilar();
        cliente.registrarAlquiler();
        return "Alquiler confirmado.\n" + alquiler.getDetalle();
    }

    public String devolverVehiculo(String placa) {
        Vehiculo vehiculo = buscarVehiculo(placa);
        if (vehiculo == null) { return "Placa inexistente."; }
        for (Alquiler alquiler : alquileres) {
            if (alquiler.isActivo() && alquiler.getVehiculo() == vehiculo) {
                alquiler.finalizar();
                return "Devolucion registrada. Estado: " + vehiculo.getEstado();
            }
        }
        return "El vehiculo no esta alquilado.";
    }

    public String terminarMantenimiento(String placa) {
        Vehiculo vehiculo = buscarVehiculo(placa);
        if (vehiculo == null) { return "Placa inexistente."; }
        if (!vehiculo.getEstado().equals("En mantenimiento")) {
            return "El vehiculo no esta en mantenimiento.";
        }
        vehiculo.terminarMantenimiento();
        return "Mantenimiento finalizado. Vehiculo disponible, acumulado en cero.";
    }

    public String consultarFlota() {
        String resultado = "";
        for (Vehiculo vehiculo : flota) {
            resultado += vehiculo.getDescripcion() + String.format(" | Tarifa: Q%.2f\n", vehiculo.getTarifaDiaria());
        }
        if (resultado.isEmpty()) { return "No hay vehiculos."; }
        return resultado;
    }

    public String consultarClientes() {
        String resultado = "";
        for (Cliente cliente : clientes) {
            resultado += cliente.getDescripcion() + " | Activos: " + contarActivos(cliente) + "\n";
        }
        if (resultado.isEmpty()) { return "No hay clientes."; }
        return resultado;
    }

    public double getIngresos() {
        double total = 0;
        for (Alquiler alquiler : alquileres) { total += alquiler.getTotal(); }
        return total;
    }

    public double getDescuentos() {
        double total = 0;
        for (Alquiler alquiler : alquileres) { total += alquiler.getDescuento(); }
        return total;
    }

    public String reporteCategorias() {
        ArrayList<String> categorias = new ArrayList<String>();
        for (Vehiculo vehiculo : flota) {
            if (!categorias.contains(vehiculo.getCategoria())) { categorias.add(vehiculo.getCategoria()); }
        }
        String resultado = "";
        for (String categoria : categorias) {
            int disponibles = 0;
            int alquilados = 0;
            int mantenimiento = 0;
            double ingresos = 0;
            for (Vehiculo vehiculo : flota) {
                if (vehiculo.getCategoria().equals(categoria)) {
                    if (vehiculo.getEstado().equals("Disponible")) { disponibles++; }
                    if (vehiculo.getEstado().equals("Alquilado")) { alquilados++; }
                    if (vehiculo.getEstado().equals("En mantenimiento")) { mantenimiento++; }
                }
            }
            for (Alquiler alquiler : alquileres) {
                if (alquiler.getVehiculo().getCategoria().equals(categoria)) { ingresos += alquiler.getTotal(); }
            }
            resultado += categoria + " | Registrados: " + (disponibles + alquilados + mantenimiento)
                    + " | Disponibles: " + disponibles + " | Alquilados: " + alquilados
                    + " | En mantenimiento: " + mantenimiento + String.format(" | Ingresos: Q%.2f\n", ingresos);
        }
        return resultado + String.format("Ingresos totales: Q%.2f\nDescuentos otorgados: Q%.2f", getIngresos(), getDescuentos());
    }

    public String reporteActivos() {
        String resultado = "";
        for (Alquiler alquiler : alquileres) {
            if (alquiler.isActivo()) { resultado += alquiler.getDetalle() + "\n"; }
        }
        if (resultado.isEmpty()) { return "No hay alquileres activos."; }
        return resultado;
    }

    public String historialCliente(String identificador) {
        Cliente cliente = buscarCliente(identificador);
        if (cliente == null) { return "Cliente inexistente."; }
        String resultado = cliente.getDescripcion() + "\n";
        double pagado = 0;
        for (Alquiler alquiler : alquileres) {
            if (alquiler.getCliente() == cliente) {
                resultado += alquiler.getDetalle() + "\n";
                pagado += alquiler.getTotal();
            }
        }
        return resultado + String.format("Total pagado en el sistema: Q%.2f", pagado);
    }
}

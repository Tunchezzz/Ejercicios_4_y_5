package modelo;

public class Automovil extends Vehiculo {
    private int pasajeros;
    private boolean automatico;

    public Automovil(String placa, String marca, String modelo, double tarifaDiaria, int diasAcumulados, int pasajeros, boolean automatico) {
        super(placa, marca, modelo, tarifaDiaria, diasAcumulados);
        this.pasajeros = pasajeros;
        this.automatico = automatico;
    }

    public String getCategoria() { return "Automovil"; }
    public String getCaracteristicas() {
        String transmision = "manual";
        if (automatico) { transmision = "automatica"; }
        return pasajeros + " pasajeros, transmision " + transmision;
    }
    public double calcularSubtotal(int dias) {
        double subtotal = getTarifaDiaria() * dias;
        if (automatico) { subtotal += 50 * dias; }
        return subtotal;
    }
    public String getLicenciaRequerida() { return "C"; }
    public int getUmbralMantenimiento() { return 30; }
    public boolean datosValidos() { return pasajeros > 0; }
}

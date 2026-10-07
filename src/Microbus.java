package modelo;

public class Microbus extends Vehiculo {
    private int pasajeros;
    private boolean conPiloto;

    public Microbus(String placa, String marca, String modelo, double tarifaDiaria, int diasAcumulados, int pasajeros, boolean conPiloto) {
        super(placa, marca, modelo, tarifaDiaria, diasAcumulados);
        this.pasajeros = pasajeros;
        this.conPiloto = conPiloto;
    }
    public String getCategoria() { return "Microbus"; }
    public String getCaracteristicas() {
        String piloto = "sin piloto";
        if (conPiloto) { piloto = "con piloto"; }
        return pasajeros + " pasajeros, " + piloto;
    }
    public double calcularSubtotal(int dias) {
        double subtotal = getTarifaDiaria() * dias;
        if (conPiloto) { subtotal += 250 * dias; }
        return subtotal;
    }
    public String getLicenciaRequerida() {
        if (conPiloto) { return "Ninguna"; }
        return "B";
    }
    public int getUmbralMantenimiento() { return 25; }
    public boolean datosValidos() { return pasajeros > 0; }
}

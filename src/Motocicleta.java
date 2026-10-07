package modelo;

public class Motocicleta extends Vehiculo {
    private int cilindraje;

    public Motocicleta(String placa, String marca, String modelo, double tarifaDiaria, int diasAcumulados, int cilindraje) {
        super(placa, marca, modelo, tarifaDiaria, diasAcumulados);
        this.cilindraje = cilindraje;
    }
    public String getCategoria() { return "Motocicleta"; }
    public String getCaracteristicas() { return cilindraje + " cc"; }
    public double calcularSubtotal(int dias) {
        double subtotal = getTarifaDiaria() * dias;
        if (cilindraje > 250) { subtotal += 75; }
        return subtotal;
    }
    public String getLicenciaRequerida() { return "M"; }
    public int getUmbralMantenimiento() { return 20; }
    public boolean datosValidos() { return cilindraje > 0; }
}

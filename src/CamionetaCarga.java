package modelo;

public class CamionetaCarga extends Vehiculo {
    private double capacidadToneladas;

    public CamionetaCarga(String placa, String marca, String modelo, double tarifaDiaria, int diasAcumulados, double capacidadToneladas) {
        super(placa, marca, modelo, tarifaDiaria, diasAcumulados);
        this.capacidadToneladas = capacidadToneladas;
    }
    public String getCategoria() { return "Camioneta de carga"; }
    public String getCaracteristicas() { return capacidadToneladas + " toneladas"; }
    public double calcularSubtotal(int dias) {
        return (getTarifaDiaria() + 100 * capacidadToneladas) * dias;
    }
    public String getLicenciaRequerida() { return "B"; }
    public int getUmbralMantenimiento() { return 15; }
    public boolean datosValidos() {
        return capacidadToneladas > 0 && Double.isFinite(capacidadToneladas);
    }
}

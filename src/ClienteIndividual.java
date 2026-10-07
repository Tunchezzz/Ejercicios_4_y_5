package modelo;

public class ClienteIndividual extends Cliente {
    public ClienteIndividual(String dpi, String nombre, String licencias, int alquileresPrevios) {
        super(dpi, nombre, licencias, alquileresPrevios);
    }
    public double getPorcentajeDescuento() {
        if (getAlquileresConfirmados() >= 3) { return 0.05; }
        return 0;
    }
    public int getLimiteActivos() { return 1; }
    public String getCaracteristicas() { return "Individual"; }
    public boolean datosValidos() { return getIdentificador().matches("[0-9]{13}"); }
}

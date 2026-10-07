package modelo;

public class ClienteCorporativo extends Cliente {
    private String nombreEmpresa;
    private String contacto;

    public ClienteCorporativo(String nit, String nombre, String licencias, String nombreEmpresa, String contacto) {
        super(nit, nombre, licencias, 0);
        this.nombreEmpresa = nombreEmpresa;
        this.contacto = contacto;
    }
    public double getPorcentajeDescuento() { return 0.10; }
    public int getLimiteActivos() { return 3; }
    public String getCaracteristicas() {
        return "Corporativo | Empresa: " + nombreEmpresa + " | Contacto: " + contacto;
    }
    public boolean datosValidos() {
        return !nombreEmpresa.trim().isEmpty() && !contacto.trim().isEmpty();
    }
}

package modelo;

public abstract class Cliente {
    private String identificador;
    private String nombre;
    private String licencias;
    private int alquileresConfirmados;

    public Cliente(String identificador, String nombre, String licencias, int alquileresConfirmados) {
        this.identificador = identificador.trim().toUpperCase();
        this.nombre = nombre;
        this.licencias = licencias.trim().toUpperCase();
        this.alquileresConfirmados = alquileresConfirmados;
    }
    public String getIdentificador() { return identificador; }
    public int getAlquileresConfirmados() { return alquileresConfirmados; }
    public String getDescripcion() {
        return identificador + " | " + nombre + " | Licencias: " + licencias
                + " | Confirmados: " + alquileresConfirmados + " | " + getCaracteristicas();
    }
    public boolean tieneLicencia(String requerida) {
        if (requerida.equals("Ninguna")) { return true; }
        if (requerida.equals("C")) {
            return licencias.contains("A") || licencias.contains("B") || licencias.contains("C");
        }
        if (requerida.equals("B")) {
            return licencias.contains("A") || licencias.contains("B");
        }
        return licencias.contains(requerida);
    }
    public boolean licenciasValidas() { return licencias.matches("[ABCM]+"); }
    void registrarAlquiler() { alquileresConfirmados++; }
    public abstract double getPorcentajeDescuento();
    public abstract int getLimiteActivos();
    public abstract String getCaracteristicas();
    public abstract boolean datosValidos();
}

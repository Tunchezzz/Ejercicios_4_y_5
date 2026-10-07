package modelo;

public abstract class Vehiculo {
    private String placa;
    private String marca;
    private String modelo;
    private double tarifaDiaria;
    private String estado;
    private int diasAcumulados;

    public Vehiculo(String placa, String marca, String modelo, double tarifaDiaria, int diasAcumulados) {
        this.placa = placa.trim().toUpperCase();
        this.marca = marca;
        this.modelo = modelo;
        this.tarifaDiaria = tarifaDiaria;
        this.diasAcumulados = diasAcumulados;
        estado = "Disponible";
    }

    public String getPlaca() { return placa; }
    public double getTarifaDiaria() { return tarifaDiaria; }
    public String getEstado() { return estado; }
    public int getDiasAcumulados() { return diasAcumulados; }

    public String getDescripcion() {
        return getCategoria() + " | " + placa + " | " + marca + " " + modelo
                + " | " + getCaracteristicas() + " | " + estado
                + " | Dias acumulados: " + diasAcumulados;
    }

    void alquilar() { estado = "Alquilado"; }

    void devolver(int dias) {
        diasAcumulados += dias;
        if (diasAcumulados >= getUmbralMantenimiento()) {
            estado = "En mantenimiento";
        } else {
            estado = "Disponible";
        }
    }

    void terminarMantenimiento() {
        diasAcumulados = 0;
        estado = "Disponible";
    }

    public abstract String getCategoria();
    public abstract String getCaracteristicas();
    public abstract double calcularSubtotal(int dias);
    public abstract String getLicenciaRequerida();
    public abstract int getUmbralMantenimiento();
    public abstract boolean datosValidos();
}

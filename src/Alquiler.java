package modelo;

public class Alquiler {
    private int numero;
    private Cliente cliente;
    private Vehiculo vehiculo;
    private int dias;
    private double subtotal;
    private double descuento;
    private double total;
    private boolean activo;

    public Alquiler(int numero, Cliente cliente, Vehiculo vehiculo, int dias) {
        this.numero = numero;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.dias = dias;
        subtotal = Math.round(vehiculo.calcularSubtotal(dias) * 100.0) / 100.0;
        descuento = Math.round(subtotal * cliente.getPorcentajeDescuento() * 100.0) / 100.0;
        total = Math.round((subtotal - descuento) * 100.0) / 100.0;
        activo = true;
    }
    public Cliente getCliente() { return cliente; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public double getDescuento() { return descuento; }
    public double getTotal() { return total; }
    public boolean isActivo() { return activo; }
    public String getDetalle() {
        String estado = "Finalizado";
        if (activo) { estado = "Activo"; }
        return "Alquiler " + numero + " | Cliente: " + cliente.getIdentificador()
                + " | Placa: " + vehiculo.getPlaca() + " | Dias: " + dias
                + String.format(" | Subtotal: Q%.2f | Descuento: Q%.2f | Total: Q%.2f", subtotal, descuento, total)
                + " | " + estado;
    }
    void finalizar() {
        activo = false;
        vehiculo.devolver(dias);
    }
}

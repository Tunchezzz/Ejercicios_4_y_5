package main;

import java.util.Locale;
import modelo.Empresa;
import vista.Vista;
import controlador.Controlador;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Empresa empresa = new Empresa();
        empresa.cargarDatosIniciales();
        Vista vista = new Vista();
        Controlador controlador = new Controlador(empresa, vista);
        controlador.iniciar();
    }
}

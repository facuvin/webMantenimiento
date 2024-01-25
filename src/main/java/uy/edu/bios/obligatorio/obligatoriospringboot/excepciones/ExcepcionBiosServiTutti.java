package uy.edu.bios.obligatorio.obligatoriospringboot.excepciones;

public class ExcepcionBiosServiTutti extends Exception {
    
    public ExcepcionBiosServiTutti() {

    }

    public ExcepcionBiosServiTutti(String mensaje) {
        super(mensaje);
    }

    public ExcepcionBiosServiTutti(String mensaje, Exception excepcionInterna) {
        super(mensaje, excepcionInterna);
    }
}
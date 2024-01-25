package uy.edu.bios.obligatorio.obligatoriospringboot.excepciones;

public class ExcepcionTieneVinculos extends ExcepcionBiosServiTutti {

    public ExcepcionTieneVinculos() {

    }

    public ExcepcionTieneVinculos(String mensaje) {
        super(mensaje);
    }

    public ExcepcionTieneVinculos(String mensaje, Exception excepcionInterna) {
        super(mensaje, excepcionInterna);
    }

}

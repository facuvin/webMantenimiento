package uy.edu.bios.obligatorio.obligatoriospringboot.excepciones;

public class ExcepcionNoExiste extends ExcepcionBiosServiTutti {

    public ExcepcionNoExiste() {

    }

    public ExcepcionNoExiste(String mensaje) {
        super(mensaje);
    }

    public ExcepcionNoExiste(String mensaje, Exception excepcionInterna) {
        super(mensaje, excepcionInterna);
    }

}

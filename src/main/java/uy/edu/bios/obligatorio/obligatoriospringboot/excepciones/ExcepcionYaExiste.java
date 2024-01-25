package uy.edu.bios.obligatorio.obligatoriospringboot.excepciones;

public class ExcepcionYaExiste extends ExcepcionBiosServiTutti {

    public ExcepcionYaExiste() {

    }

    public ExcepcionYaExiste(String mensaje) {
        super(mensaje);
    }

    public ExcepcionYaExiste(String mensaje, Exception excepcionInterna) {
        super(mensaje, excepcionInterna);
    }
}
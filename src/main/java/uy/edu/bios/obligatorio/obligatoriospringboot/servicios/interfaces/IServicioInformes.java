package uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Informe;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;

public interface IServicioInformes {
    void registrarInforme (Informe informe) throws ExcepcionBiosServiTutti;

    Informe obtener(Integer numeroInforme);
}

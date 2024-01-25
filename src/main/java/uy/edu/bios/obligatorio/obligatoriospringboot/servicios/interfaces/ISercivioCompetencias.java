package uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces;

import java.util.List;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Competencia;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;

public interface ISercivioCompetencias {

    List<Competencia> listar();
    Competencia obtener(Integer id);
    List<Competencia> buscar(String criterio);
    void agregar(Competencia competencia) throws ExcepcionBiosServiTutti;
    void modificar(Competencia competencia) throws ExcepcionBiosServiTutti;
    void eliminar(Integer id) throws ExcepcionBiosServiTutti;
    
}

package uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces;

import java.util.List;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Tecnico;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;

public interface IServicioTecnicos {
    
    List<Tecnico> listar();
    List<Tecnico> listarActivos();
    Tecnico obtener(String nomUsu);
    List<Tecnico> buscar(String criterio);
    void agregar(Tecnico tecnico) throws ExcepcionBiosServiTutti;
    void modificar(Tecnico tecnico) throws ExcepcionBiosServiTutti;
    void eliminar(String nomUsu) throws ExcepcionBiosServiTutti;
}

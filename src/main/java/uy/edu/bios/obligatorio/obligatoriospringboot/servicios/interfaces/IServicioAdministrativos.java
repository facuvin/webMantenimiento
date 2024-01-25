package uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces;

import java.util.List;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Administrativo;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;

public interface IServicioAdministrativos {
    
    List<Administrativo> listar();
    List<Administrativo> buscar(String criterio);
    Administrativo obtener(String nomUsu);
    void agregar(Administrativo administrativo) throws ExcepcionBiosServiTutti;
    void modificar(Administrativo administrativo) throws ExcepcionBiosServiTutti;
    void eliminar(String nomUsu) throws ExcepcionBiosServiTutti;
}

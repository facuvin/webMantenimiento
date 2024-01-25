package uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces;

import java.util.List;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Tecnico;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Visita;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;

public interface IServicioSolicitudes {
    
    List<Visita> listarSolicitudesPorTecnico(Tecnico tecnico);
    List<Visita> listarSolicitudesSinAsignar();
    List<Visita> listar();
    Visita obtener(Integer numero);
    void registrarSolicitud(Visita visita) throws ExcepcionBiosServiTutti;
    void asignarTecnicoASolicitud(String nombreUsuario, Integer numero) throws ExcepcionBiosServiTutti;
}

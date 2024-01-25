package uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces;

import java.util.List;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Cliente;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;

public interface IServicioClientes {
    
    List<Cliente> listar();
    List<Cliente> buscar(String criterio);
    Cliente obtener(String nomUsu);
    void agregar(Cliente cliente) throws ExcepcionBiosServiTutti;
    void modificar(Cliente cliente) throws ExcepcionBiosServiTutti;
}

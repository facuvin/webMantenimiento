package uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces;

import java.util.List;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.CancelacionContrato;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Cliente;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Contrato;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;

public interface IServicioContratos {
    
    void agregarContrato(Contrato contrato) throws ExcepcionBiosServiTutti;
    void cancelarContrato(CancelacionContrato cancelacionContrato) throws ExcepcionBiosServiTutti;
    List<Contrato> listarContratosCliente(Cliente cliente);
    Contrato obtener(Integer numero);
}

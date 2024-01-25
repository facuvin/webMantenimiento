package uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces;

import java.util.List;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Cliente;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Pago;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;

public interface IServicioPagos {
    
    void registrarPago(Pago pago) throws ExcepcionBiosServiTutti;
    List<Pago> listarPagos(Cliente cliente);
    Pago obtener(Integer numero);
}

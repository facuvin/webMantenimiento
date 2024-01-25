package uy.edu.bios.obligatorio.obligatoriospringboot.servicios.clases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.CancelacionContrato;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Cliente;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Contrato;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionYaExiste;
import uy.edu.bios.obligatorio.obligatoriospringboot.repositorios.IRepositorioCancelacionesContratos;
import uy.edu.bios.obligatorio.obligatoriospringboot.repositorios.IRepositorioContratos;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.IServicioContratos;

@Service
public class ServicioContratos implements IServicioContratos {

    @Autowired
    IRepositorioContratos repositorioContratos;

    @Autowired
    IRepositorioCancelacionesContratos repositorioCancelacionesContratos;

    @Override
    public void agregarContrato(Contrato contrato) throws ExcepcionBiosServiTutti {
        Contrato contratoExistente = repositorioContratos.findById(contrato.getNumero()).orElse(null);

        List<Contrato> contratosCliente=this.listarContratosCliente(contrato.getCliente());

        if (contratoExistente != null) {
            throw new ExcepcionYaExiste("El contrato ya existe");
        }

        Contrato contratoActivo = repositorioContratos.tieneContratoActivo(contrato.getCliente().getNombreUsuario());

        if (contratoActivo != null) {
            throw new ExcepcionBiosServiTutti("El cliente ya tiene un contrato activo");
        }
        for(Contrato c : contratosCliente){
            if(c.getCancelacion().getFecha().isAfter(contrato.getFechaFirma())) {
                throw new ExcepcionBiosServiTutti("El contrato no puede tener una fecha menor a "+c.getCancelacion().getFecha());
            }
        }


        repositorioContratos.save(contrato);
    }

    @Override
    public void cancelarContrato(CancelacionContrato cancelacionContrato) throws ExcepcionBiosServiTutti {
        CancelacionContrato cancelacionExistene = repositorioCancelacionesContratos.findById(cancelacionContrato.getNumero()).orElse(null);

        if (cancelacionExistene != null) {
            throw new ExcepcionYaExiste("La cancelacion ya existe");
        }
        if(cancelacionContrato.getContrato().getFechaFirma().isAfter(cancelacionContrato.getFecha())){
            throw new ExcepcionBiosServiTutti("La fecha de cancelacion no puede ser menor a "+ cancelacionContrato.getContrato().getFechaFirma());
        }

        repositorioCancelacionesContratos.save(cancelacionContrato);
    }
    
    @Override
    public List<Contrato> listarContratosCliente(Cliente cliente) {
        return repositorioContratos.contratosCliente(cliente.getNombreUsuario());
    }

    @Override
    public Contrato obtener(Integer numero) {
        return repositorioContratos.findById(numero).orElse(null);
    }
}

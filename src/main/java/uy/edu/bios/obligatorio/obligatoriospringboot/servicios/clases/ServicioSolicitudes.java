package uy.edu.bios.obligatorio.obligatoriospringboot.servicios.clases;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Pago;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Tecnico;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Visita;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionNoExiste;
import uy.edu.bios.obligatorio.obligatoriospringboot.repositorios.IRepositorioPagos;
import uy.edu.bios.obligatorio.obligatoriospringboot.repositorios.IRepositorioTecnicos;
import uy.edu.bios.obligatorio.obligatoriospringboot.repositorios.IRepositorioVisitas;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.IServicioSolicitudes;

@Service
public class ServicioSolicitudes implements IServicioSolicitudes {
    
    @Autowired
    IRepositorioVisitas repositorioVisitas;

    @Autowired
    IRepositorioTecnicos repositorioTecnicos;

    @Autowired
    IRepositorioPagos repositorioPagos;

    @Override
    public List<Visita> listarSolicitudesPorTecnico(Tecnico tecnico) {
        return repositorioVisitas.listarSolicitudesPorTecnico(tecnico.getNombreUsuario());
    }

    @Override
    public List<Visita> listarSolicitudesSinAsignar() {
        return repositorioVisitas.listarSolicitudesSinAsignar();
    }

    @Override
    public Visita obtener(Integer numero) {
        return repositorioVisitas.findById(numero).orElse(null);
    }

    @Override
    public void registrarSolicitud(Visita visita) throws ExcepcionBiosServiTutti {

        List<Pago> pagosCliente = repositorioPagos.listar(visita.getCliente().getNombreUsuario());
        
        visita.setFechayHora(LocalDateTime.now());
        Pago pagoDelMes = null;

        for (Pago p : pagosCliente) {
            if (!p.getFecha().isBefore(LocalDate.now().minusDays(30))) {
                pagoDelMes = p;
                break;
            }
        }

        if (pagoDelMes == null) {
            throw new ExcepcionBiosServiTutti("El cliente mantiene deudas pendientes, no se puede realizar la solicitud");
        }

        repositorioVisitas.save(visita);
    }

    @Override
    public void asignarTecnicoASolicitud(String nombreUsuario, Integer numero) throws ExcepcionBiosServiTutti {
        Visita visitaExistente = repositorioVisitas.findById(numero).orElse(null);
        
        if (visitaExistente == null) {
            throw new ExcepcionNoExiste("La solicitud de visita no existe.");
        }

        Tecnico tecnicoExistente = repositorioTecnicos.findById(nombreUsuario).orElse(null);

        if (tecnicoExistente == null) {
            throw new ExcepcionNoExiste("El tecnico indica no existe.");
        }

        visitaExistente.setTecnico(tecnicoExistente);

        repositorioVisitas.save(visitaExistente);
    }

    @Override
    public List<Visita> listar() {
        return repositorioVisitas.findAll();
    }
    
}

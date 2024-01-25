package uy.edu.bios.obligatorio.obligatoriospringboot.servicios.clases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Rol;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Tecnico;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionNoExiste;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionYaExiste;
import uy.edu.bios.obligatorio.obligatoriospringboot.repositorios.IRepositorioTecnicos;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.IServicioTecnicos;

@Service
public class ServicioTecnicos implements IServicioTecnicos {

    @Autowired
    IRepositorioTecnicos repositorioTecnicos;

    @Autowired
    ServicioComprovacionUsuarios comprovacionUsuarios;

    @Override
    public List<Tecnico> listar() {
        return repositorioTecnicos.findAll();
    }

    @Override
    public Tecnico obtener(String nomUsu) {
        return repositorioTecnicos.findById(nomUsu).orElse(null);
    }

    @Override
    public void agregar(Tecnico tecnico) throws ExcepcionBiosServiTutti {
        tecnico.setActivo(true);

        Tecnico tecnicoExistente = repositorioTecnicos.findById(tecnico.getNombreUsuario()).orElse(null);


        if (tecnicoExistente != null && tecnicoExistente.isActivo()) {
            throw new ExcepcionYaExiste("El tecnico ya existe.");
        } else if (tecnicoExistente != null && !tecnicoExistente.isActivo()) {
            throw new ExcepcionYaExiste("El tecnico ya existe y esta inactivo");
        }else if(comprovacionUsuarios.comprobarRepetido(tecnico.getNombreUsuario())){
            throw new ExcepcionYaExiste("El nombre de usuario ya existe.");
        }

        tecnico.getRoles().add(new Rol("tecnico"));

        repositorioTecnicos.save(tecnico);
    }

    @Override
    public void modificar(Tecnico tecnico) throws ExcepcionBiosServiTutti {
        tecnico.setActivo(true);

        Tecnico tecnicoExistente = repositorioTecnicos.findById(tecnico.getNombreUsuario()).orElse(null);

        if (tecnicoExistente == null) {
            throw new ExcepcionNoExiste("El tecnico no existe.");
        }

        tecnico.getRoles().clear();

        for (Rol r : tecnicoExistente.getRoles()) {
            tecnico.getRoles().add(r);
        }

        repositorioTecnicos.save(tecnico);
    }

    @Override
    public void eliminar(String nomUsu) throws ExcepcionBiosServiTutti {
        Tecnico tecnicoExistente = repositorioTecnicos.findById(nomUsu).orElse(null);

        if (tecnicoExistente == null) {
            throw new ExcepcionNoExiste("El tecnico no existe.");
        }

        if (tecnicoExistente.getVisitasAsignadas().size() == 0) {
            repositorioTecnicos.deleteById(nomUsu);
        } else {
            tecnicoExistente.setActivo(false);

            repositorioTecnicos.save(tecnicoExistente);
        }
    }

    @Override
    public List<Tecnico> listarActivos() {
        return repositorioTecnicos.listarActivos();
    }

    @Override
    public List<Tecnico> buscar(String criterio) {
        if (criterio == null || criterio.isBlank()) {
            return listar();
        }

        return repositorioTecnicos.buscar(criterio);
    }
    
}

package uy.edu.bios.obligatorio.obligatoriospringboot.servicios.clases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Administrativo;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Rol;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionNoExiste;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionYaExiste;
import uy.edu.bios.obligatorio.obligatoriospringboot.repositorios.IRepositorioAdministrativos;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.IServicioAdministrativos;

@Service
public class ServicioAdministrativos implements IServicioAdministrativos {

    @Autowired
    IRepositorioAdministrativos repositorioAdministrativos;

    @Autowired
    ServicioComprovacionUsuarios comprovacionUsuarios;

    @Override
    public List<Administrativo> listar() {
        return repositorioAdministrativos.findAll();
    }

    @Override
    public Administrativo obtener(String nomUsu) {
        return repositorioAdministrativos.findById(nomUsu).orElse(null);
    }

    @Override
    public void agregar(Administrativo administrativo) throws ExcepcionBiosServiTutti {
        administrativo.setActivo(true);
        Administrativo admExistente = repositorioAdministrativos.findById(administrativo.getNombreUsuario()).orElse(null);

        if (admExistente != null) {
            throw new ExcepcionYaExiste("El administrativo ya existe.");
        }else if(comprovacionUsuarios.comprobarRepetido(administrativo.getNombreUsuario())){
            throw new ExcepcionYaExiste("El nombre de usuario ya existe.");
        }

        administrativo.getRoles().add(new Rol("administrativo"));

        repositorioAdministrativos.save(administrativo);
    }

    @Override
    public void modificar(Administrativo administrativo) throws ExcepcionBiosServiTutti {
        administrativo.setActivo(true);
        Administrativo admExistente = repositorioAdministrativos.findById(administrativo.getNombreUsuario()).orElse(null);

        if (admExistente == null) {
            throw new ExcepcionNoExiste("El administrativo no existe.");
        }

        administrativo.getRoles().clear();

        for (Rol r : admExistente.getRoles()) {
            administrativo.getRoles().add(r);
        }

        repositorioAdministrativos.save(administrativo);
    }

    @Override
    public void eliminar(String nomUsu) throws ExcepcionBiosServiTutti {
        
        Administrativo admExistente = repositorioAdministrativos.findById(nomUsu).orElse(null);

        if (admExistente == null) {
            throw new ExcepcionNoExiste("El administrativo no existe.");
        }

        repositorioAdministrativos.deleteById(nomUsu);
    }

    @Override
    public List<Administrativo> buscar(String criterio) {
        if (criterio == null || criterio.isBlank()) {
            return listar();
        }

        return repositorioAdministrativos.buscar(criterio);
    }
    
}

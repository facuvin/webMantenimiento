package uy.edu.bios.obligatorio.obligatoriospringboot.servicios.clases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Competencia;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionNoExiste;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionTieneVinculos;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionYaExiste;
import uy.edu.bios.obligatorio.obligatoriospringboot.repositorios.IRepositorioCompetencias;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.ISercivioCompetencias;

@Service
public class ServicioCompetencias implements ISercivioCompetencias {

    @Autowired
    IRepositorioCompetencias repositorioCompetencias;

    @Override
    public List<Competencia> listar() {
        return repositorioCompetencias.findAll();
    }

    @Override
    public Competencia obtener(Integer id) {
        return repositorioCompetencias.findById(id).orElse(null);
    }

    @Override
    public void agregar(Competencia competencia) throws ExcepcionBiosServiTutti {
        Competencia c = repositorioCompetencias.findById(competencia.getId()).orElse(null);

        if (c != null) {
            throw new ExcepcionYaExiste("La competencia ya existe.");
        }

        repositorioCompetencias.save(competencia);
    }

    @Override
    public void modificar(Competencia competencia) throws ExcepcionBiosServiTutti {
        Competencia c = repositorioCompetencias.findById(competencia.getId()).orElse(null);

        if (c == null) {
            throw new ExcepcionNoExiste("La competencia no existe.");
        }

        repositorioCompetencias.save(competencia);
    }

    @Override
    public void eliminar(Integer id) throws ExcepcionBiosServiTutti {
        Competencia c = repositorioCompetencias.findById(id).orElse(null);

        if (c == null) {
            throw new ExcepcionNoExiste("La competencia no existe.");
        }

        try { //IGUAL A ELIMINAR PEDIDO, REVISAR
            repositorioCompetencias.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ExcepcionTieneVinculos("La competencia esta asociada con al menos una solicitud y/o tecnico.");
        }
    }

    @Override
    public List<Competencia> buscar(String criterio) {
        if (criterio == null || criterio.isBlank()) {
            return listar();
        }

        return repositorioCompetencias.buscar(criterio);
    }
    
}

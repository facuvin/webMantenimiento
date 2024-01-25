package uy.edu.bios.obligatorio.obligatoriospringboot.servicios.clases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Cliente;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Rol;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionNoExiste;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionYaExiste;
import uy.edu.bios.obligatorio.obligatoriospringboot.repositorios.IRepositorioClientes;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.IServicioClientes;

@Service
public class ServicioClientes implements IServicioClientes {

    @Autowired
    IRepositorioClientes repositorioClientes;

    @Autowired
    ServicioComprovacionUsuarios comprovacionUsuarios;

    @Override
    public List<Cliente> listar() {
        return repositorioClientes.findAll();
    }

    @Override
    public Cliente obtener(String nomUsu) {
        return repositorioClientes.findById(nomUsu).orElse(null);
    }

    @Override
    public void agregar(Cliente cliente) throws ExcepcionBiosServiTutti {
        cliente.setActivo(true);
        Cliente clienteExistente = repositorioClientes.findById(cliente.getNombreUsuario()).orElse(null);

        if (clienteExistente != null) {
            throw new ExcepcionYaExiste("El cliente ya existe.");
        }
        else if(comprovacionUsuarios.comprobarRepetido(cliente.getNombreUsuario())){
            throw new ExcepcionYaExiste("El nombre de usuario ya existe.");
        }

        cliente.getRoles().add(new Rol("cliente"));

        repositorioClientes.save(cliente);
    }

    @Override
    public void modificar(Cliente cliente) throws ExcepcionBiosServiTutti {
        cliente.setActivo(true);
        Cliente clienteExistente = repositorioClientes.findById(cliente.getNombreUsuario()).orElse(null);

        if (clienteExistente == null) {
            throw new ExcepcionNoExiste("El cliente no existe.");
        }

        cliente.getRoles().clear();

        for (Rol r : clienteExistente.getRoles()) {
            cliente.getRoles().add(r);
        }

        repositorioClientes.save(cliente);
    }

    @Override
    public List<Cliente> buscar(String criterio) {
        
        if (criterio == null || criterio.isBlank()) {
            return listar();
        }

        return repositorioClientes.buscar(criterio);
    }
    
}

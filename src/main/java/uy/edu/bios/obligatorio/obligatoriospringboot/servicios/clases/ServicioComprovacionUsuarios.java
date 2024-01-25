package uy.edu.bios.obligatorio.obligatoriospringboot.servicios.clases;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Rol;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Usuario;
import uy.edu.bios.obligatorio.obligatoriospringboot.repositorios.IRepositorioAdministrativos;
import uy.edu.bios.obligatorio.obligatoriospringboot.repositorios.IRepositorioClientes;
import uy.edu.bios.obligatorio.obligatoriospringboot.repositorios.IRepositorioTecnicos;

@Service
public class ServicioComprovacionUsuarios implements  UserDetailsService{

    @Autowired
    IRepositorioAdministrativos repositorioAdministrativos;

    @Autowired
    IRepositorioClientes repositorioClientes;

    @Autowired
    IRepositorioTecnicos repositorioTecnicos;

    public Boolean comprobarRepetido(String nomUsu){  
         
         Usuario usuario=repositorioAdministrativos.findById(nomUsu).orElse(null);

        if (usuario == null) {
            usuario = repositorioClientes.findById(nomUsu).orElse(null);
        }
        if (usuario == null) {
            usuario = repositorioTecnicos.findById(nomUsu).orElse(null);
        }         
         if(usuario==null){
            return false;
         }
         else{
            return true;
         }              
    }

    @Override
    public UserDetails loadUserByUsername(String username) 
            throws UsernameNotFoundException{

        Usuario usuario=repositorioAdministrativos.findById(username).orElse(null);

        if (usuario == null) {
            usuario = repositorioClientes.findById(username).orElse(null);
        }
        if (usuario == null  ) {
            usuario = repositorioTecnicos.findById(username).orElse(null);
        }

        if (usuario == null|| !usuario.isActivo()) throw new UsernameNotFoundException("El usuario no existe.");

        List<GrantedAuthority> roles = new ArrayList<>();      
        

        for (Rol r : usuario.getRoles()) {
            roles.add(new SimpleGrantedAuthority(r.getNombreRol()));
        }

        return new User(usuario.getNombreUsuario(), usuario.getPassword(), usuario.isActivo(), true, true, true, roles);
    }
}


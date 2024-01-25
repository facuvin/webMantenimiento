package uy.edu.bios.obligatorio.obligatoriospringboot.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Administrativo;

public interface IRepositorioAdministrativos extends JpaRepository<Administrativo, String>{
    
    @Override
    @EntityGraph(type = EntityGraphType.LOAD, attributePaths = {"roles"})
    Optional<Administrativo> findById(String nombreUsuario);


    @Query(value = "select a from Administrativo a where nombreUsuario = ?1 or nombre like concat('%', ?1, '%') or apellido like concat('%', ?1, '%')")
    List<Administrativo> buscar(String criterio);
}

package uy.edu.bios.obligatorio.obligatoriospringboot.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Tecnico;

public interface IRepositorioTecnicos extends JpaRepository<Tecnico, String> {

    @Override
    @EntityGraph(type = EntityGraphType.LOAD, attributePaths = {"competencias"})
    List<Tecnico> findAll();

    @Override
    @EntityGraph(type = EntityGraphType.LOAD, attributePaths = {"competencias", "roles"})
    Optional<Tecnico> findById(String id);


    @Query("SELECT t FROM Tecnico t JOIN Usuario u ON t.nombreUsuario = u.nombreUsuario WHERE t.activo = true")
    List<Tecnico> listarActivos();
    
    @Query(value = "select t from Tecnico t where nombreUsuario = ?1 or nombre like concat('%', ?1, '%') or apellido like concat('%', ?1, '%')")
    List<Tecnico> buscar(String criterio);
}

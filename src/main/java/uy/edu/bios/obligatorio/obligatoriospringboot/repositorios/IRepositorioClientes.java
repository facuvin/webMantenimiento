package uy.edu.bios.obligatorio.obligatoriospringboot.repositorios;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.transaction.annotation.Transactional;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Cliente;

@Transactional
public interface IRepositorioClientes extends JpaRepository<Cliente, String>{
    @Override
    @EntityGraph(type = EntityGraphType.LOAD, attributePaths = {"roles"})
    Optional<Cliente> findById(String nombreUsuario);

    @Query(value = "select c from Cliente c where nombreUsuario = ?1 or nombre like concat('%', ?1, '%') or apellido like concat('%', ?1, '%')")
    List<Cliente> buscar(String criterio);

    @Modifying
    @Query(value = "UPDATE Cliente c SET c.horasTecnico = :horas WHERE c.nombreUsuario = :nombre")
    void setearHoras(Integer horas, String nombre );

}

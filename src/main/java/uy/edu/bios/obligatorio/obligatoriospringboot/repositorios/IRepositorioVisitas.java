package uy.edu.bios.obligatorio.obligatoriospringboot.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Visita;

public interface IRepositorioVisitas extends JpaRepository<Visita, Integer>{

    @Override
    @EntityGraph(type = EntityGraphType.LOAD, attributePaths = {"cliente", "tecnico", "competencias"})
    List<Visita> findAll();

    @Override
    @EntityGraph(type = EntityGraphType.LOAD, attributePaths = {"cliente", "tecnico", "competencias"})
    Optional<Visita> findById(Integer id);


    @Query("SELECT v FROM Visita v " +
    "INNER JOIN v.tecnico vt " +
    "LEFT JOIN v.informe i " +
    "WHERE vt.nombreUsuario = :p_nombre_tecnico " +
    "AND (i IS NULL OR i.numero IS NULL)")
    List<Visita> listarSolicitudesPorTecnico(@Param("p_nombre_tecnico") String nombreTecnico);

    @Query("SELECT v FROM Visita v WHERE v.tecnico IS NULL")
    List<Visita> listarSolicitudesSinAsignar();

}

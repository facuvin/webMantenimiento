package uy.edu.bios.obligatorio.obligatoriospringboot.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Competencia;

public interface IRepositorioCompetencias extends JpaRepository<Competencia, Integer>{
    
    @Query(value = "select c from Competencia c where cast(id as String) = ?1 or nombre like concat('%', ?1, '%')")
    List<Competencia> buscar(String criterio);
}

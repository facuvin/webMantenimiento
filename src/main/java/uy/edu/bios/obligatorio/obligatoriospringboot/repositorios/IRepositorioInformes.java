package uy.edu.bios.obligatorio.obligatoriospringboot.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Informe;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositorioInformes extends JpaRepository<Informe, Integer>{
    
    @Override
    @EntityGraph(type = EntityGraphType.LOAD, attributePaths = {"visita"})
    List<Informe> findAll();

    @Override
    @EntityGraph(type = EntityGraphType.LOAD, attributePaths = {"visita"})
    Optional<Informe> findById(Integer id);
}

package uy.edu.bios.obligatorio.obligatoriospringboot.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.CancelacionContrato;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositorioCancelacionesContratos extends JpaRepository<CancelacionContrato, Integer>{

    @Override
    @EntityGraph(type = EntityGraphType.LOAD, attributePaths = {"contrato"})
    List<CancelacionContrato> findAll();

    @Override
    @EntityGraph(type = EntityGraphType.LOAD, attributePaths = {"contrato"})
    Optional<CancelacionContrato> findById(Integer id);
    
}

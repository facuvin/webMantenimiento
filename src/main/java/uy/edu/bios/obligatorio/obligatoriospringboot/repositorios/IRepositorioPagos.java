package uy.edu.bios.obligatorio.obligatoriospringboot.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Pago;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IRepositorioPagos extends JpaRepository<Pago, Integer>{

    @Override
    @EntityGraph(type = EntityGraphType.LOAD, attributePaths = {"cliente"})
    List<Pago> findAll();

    @Override
    @EntityGraph(type = EntityGraphType.LOAD, attributePaths = {"cliente"})
    Optional<Pago> findById(Integer id);

    //@Procedure("listar_pagos")
    @Query("SELECT p FROM Pago p WHERE p.cliente.nombreUsuario = ?1")
    List<Pago> listar(String nombreCliente);
}

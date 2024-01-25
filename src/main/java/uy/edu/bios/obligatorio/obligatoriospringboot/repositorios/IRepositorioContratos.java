package uy.edu.bios.obligatorio.obligatoriospringboot.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Contrato;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IRepositorioContratos extends JpaRepository<Contrato, Integer>{
    
    @Override
    @EntityGraph(type = EntityGraphType.LOAD, attributePaths = {"cliente","cancelacion"})
    List<Contrato> findAll();

    @Override
    @EntityGraph(type = EntityGraphType.LOAD, attributePaths = {"cliente","cancelacion"})
    Optional<Contrato> findById(Integer id);

    // @Procedure("tiene_contrato_activo")
    @Query("SELECT c FROM Contrato c WHERE c.cliente.nombreUsuario = ?1 AND c.numero NOT IN (SELECT canc.contrato.numero FROM CancelacionContrato canc)")
    Contrato tieneContratoActivo(String nombreCliente);

    @Query("SELECT c FROM Contrato c WHERE c.cliente.nombreUsuario = ?1 Order by c.fechaFirma desc")
    List<Contrato> contratosCliente(String nombreCliente);
    
}

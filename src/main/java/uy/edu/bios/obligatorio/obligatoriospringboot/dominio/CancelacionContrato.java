package uy.edu.bios.obligatorio.obligatoriospringboot.dominio;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cancelaciones")
public class CancelacionContrato {

    @NotNull
    @Positive
    @Id
    @Column(name = "numero_cancelacion")
    private Integer numero;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDate fecha;

    @Size(max = 250)
    @Column(length = 250)
    private String motivo;

    //@NotNull
    @OneToOne(optional = false)
    private Contrato contrato;

    public Integer getNumero() {
        return numero;
    }
    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    public CancelacionContrato(Integer numero, LocalDate fecha, String motivo, Contrato contrato) {
        this.numero = numero;
        this.fecha = fecha;
        this.motivo = motivo;
        this.contrato = contrato;
    }
    public CancelacionContrato() {
        this(null, null, null, null);
 
    }
    
    public Contrato getContrato() {
        return contrato;
    }
    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    } 
    
}

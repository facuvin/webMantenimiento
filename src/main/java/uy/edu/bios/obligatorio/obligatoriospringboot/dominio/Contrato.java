package uy.edu.bios.obligatorio.obligatoriospringboot.dominio;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "contratos")
public class Contrato {

    @NotNull
    @Positive
    @Id
    private Integer numero; 

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDate fechaFirma;

    @Valid
    //@NotNull
    @ManyToOne(optional = false)
    private Cliente cliente;

    @Valid
    @OneToOne(mappedBy = "contrato")
    private CancelacionContrato cancelacion;
    
    private Boolean tieneImagen;
    
    
    public Boolean getTieneImagen() {
        return tieneImagen;
    }
    public void setTieneImagen(Boolean tieneImagen) {
        this.tieneImagen = tieneImagen;
    }
    public Integer getNumero() {
        return numero;
    }
    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    public LocalDate getFechaFirma() {
        return fechaFirma;
    }
    public void setFechaFirma(LocalDate fechaFirma) {
        this.fechaFirma = fechaFirma;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public CancelacionContrato getCancelacion() {
        return cancelacion;
    }
    public void setCancelacion(CancelacionContrato cancelacion) {
        this.cancelacion = cancelacion;
    }
    public Contrato(Integer numero, LocalDate fechaFirma, Cliente cliente, CancelacionContrato cancelacion,
            Boolean tieneImagen) {
        this.numero = numero;
        this.fechaFirma = fechaFirma;
        this.cliente = cliente;
        this.cancelacion = cancelacion;
        this.tieneImagen = tieneImagen;
    }
    public Contrato() {
        this(null, null, null, null, null);
    }
    @Override
    public String toString() {
        return "Contrato [numero=" + numero + ", fechaFirma=" + fechaFirma + ", cliente=" + cliente + ", cancelacion="
                + cancelacion + "]";
    }
    
}

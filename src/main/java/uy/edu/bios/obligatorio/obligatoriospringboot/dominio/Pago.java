package uy.edu.bios.obligatorio.obligatoriospringboot.dominio;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer numero;

    @Column(nullable = false)
    private LocalDate fecha;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal importe;

    @Size(max = 250)
    @Column(length = 250)
    private String descripcion;

    @ManyToOne
    //@JoinColumn(name = "cliente_nombre_usuario", referencedColumnName = "nombre_usuario")
    private Cliente cliente;


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
    public BigDecimal getImporte() {
        return importe;
    }
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Pago(Integer numero, LocalDate fecha, BigDecimal importe, String descripcion, Cliente cliente) {
        this.numero = numero;
        this.fecha = fecha;
        this.importe = importe;
        this.descripcion = descripcion;
        this.cliente = cliente;
    }
    public Pago() {
        this(null, null, null, null, null);
    }
    
    @Override
    public String toString() {
        return "Pago [numero=" + numero + ", fecha=" + fecha + ", importe=" + importe + ", descripcion=" + descripcion
                + ", cliente=" + cliente + "]";
    } 
    
    

}

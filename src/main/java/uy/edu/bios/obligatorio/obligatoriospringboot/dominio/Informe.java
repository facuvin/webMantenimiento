package uy.edu.bios.obligatorio.obligatoriospringboot.dominio;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "informes")
public class Informe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer numero;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime fechayHora;

    @NotBlank
    @Size(max = 250)
    @Column(nullable = false, length = 250)
    private String descripcion;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer duracion;
    
    
    @OneToOne(optional = false)
    private Visita visita;

    @Positive
    private Integer horasExtra;
       
    
    public Visita getVisita() {
        return visita;
    }
    public void setVisita(Visita visita) {
        this.visita = visita;
    }
    public Integer getNumero() {
        return numero;
    }
    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    public LocalDateTime getFechayHora() {
        return fechayHora;
    }
    public void setFechayHora(LocalDateTime fechayHora) {
        this.fechayHora = fechayHora;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Integer getDuracion() {
        return duracion;
    }
    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }
    public Integer getHorasExtra() {
        return horasExtra;
    }
    public void setHorasExtra(Integer horasExtra) {
        this.horasExtra = horasExtra;
    }
    
    public Informe(Integer numero, LocalDateTime fechayHora, String descripcion, Integer duracion, Integer horasExtra,
            Visita visita) {
        this.numero = numero;
        this.fechayHora = fechayHora;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.horasExtra = horasExtra;
        this.visita = visita;
    }
    
    public Informe() {
        this(null, null, null, null, null, null);
    }


    @Override
    public String toString() {
        return "Informe [numero=" + numero + ", fechayHora=" + fechayHora + ", descripcion=" + descripcion
                + ", duracion=" + duracion + ", horasExtra=" + horasExtra + ", visita=" + visita + "]";
    }
    
    
}

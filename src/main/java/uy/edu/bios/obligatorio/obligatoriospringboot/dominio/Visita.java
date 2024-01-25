package uy.edu.bios.obligatorio.obligatoriospringboot.dominio;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "visitas")
public class Visita {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer numero;


    @Column(nullable = false)
    private LocalDateTime fechayHora;

    @NotBlank
    @Size(max = 300)
    @Column(nullable = false, length = 300)
    private String descripcion;

    @NotNull
    @Column(nullable = false)    
    private LocalTime horaInicio;

    @NotNull
    @Column(nullable = false)
    private LocalTime horaFin;

    //@NotNull
    @Valid
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_nombre_usuario", referencedColumnName = "nombre_usuario")
    private Cliente cliente;
    
    @ManyToOne
    @JoinTable(name = "visita_tecnico", joinColumns = { @JoinColumn (name = "visita_numero") } , inverseJoinColumns = { @JoinColumn (name = "tecnico_nombre_usuario") } )
    private Tecnico tecnico;   
    
    @ManyToMany
    @JoinTable(joinColumns = { @JoinColumn (name = "visita_numero") } , inverseJoinColumns = { @JoinColumn (name = "competencia_id") } )
    private Set<Competencia> competencias;


    @OneToOne(mappedBy = "visita")    
    private Informe informe;

    public Informe getInforme() {
        return informe;
    }
    public void setInforme(Informe informe) {
        this.informe = informe;
    }
    public Set<Competencia> getCompetencias() {
        return competencias;
    }
    public void setCompetencias(Set<Competencia> competencias) {
        this.competencias = competencias;
    }
    public Tecnico getTecnico() {
        return tecnico;
    }
      public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
    public LocalTime getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }
    public LocalTime getHoraFin() {
        return horaFin;
    }
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }
    public Visita(Integer numero, LocalDateTime fechayHora, @NotBlank String descripcion, @NotNull LocalTime horaInicio,
            @NotNull LocalTime horaFin, @NotNull Cliente cliente, Tecnico tecnico, Set<Competencia> competencias,
            Informe informe) {
        this.numero = numero;
        this.fechayHora = fechayHora;
        this.descripcion = descripcion;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.cliente = cliente;
        this.tecnico = tecnico;
        this.competencias = competencias;
        this.informe = informe;
    }
    public Visita() {
        this(null, null, null, null, null, null, null, null, null);
    }    

}

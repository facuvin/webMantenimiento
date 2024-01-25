package uy.edu.bios.obligatorio.obligatoriospringboot.dominio;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tecnicos")
@PrimaryKeyJoinColumn(name = "nombre_usuario", referencedColumnName = "nombre_usuario")
public class Tecnico extends Usuario {
    
    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, length = 30, unique = true)   
    private String telefono;

    @NotEmpty
    @NotNull
    @ManyToMany
    @JoinTable(joinColumns = { @JoinColumn (name = "tecnico_nombre_usuario") } , inverseJoinColumns = { @JoinColumn (name = "competencia_id") } )
    private Set<Competencia> competencias;

    @OneToMany(mappedBy = "tecnico")
    private Set<Visita> visitasAsignadas;


    public Set<Visita> getVisitasAsignadas() {
        return visitasAsignadas;
    }

    public void setVisitasAsignadas(Set<Visita> visitasAsignadas) {
        this.visitasAsignadas = visitasAsignadas;
    }

    public Set<Competencia> getCompetencias() {
        return competencias;
    }

    public void setCompetencias(Set<Competencia> competencias) {
        this.competencias = competencias;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    

    public Tecnico() {
        this(null, null, null, null, null, false, null, null, null);
    }

    public Tecnico(String nombreUsuario, String password, String nombre, String apellido, String eMail,boolean activo,
            @NotBlank @Size(max = 30) String telefono, @NotEmpty @NotNull Set<Competencia> competencias,
            Set<Visita> visitasAsignadas) {
        super(nombreUsuario, password, nombre, apellido, eMail, activo);
        this.telefono = telefono;
        this.competencias = competencias;
        this.visitasAsignadas = visitasAsignadas;
    }
   


    
}

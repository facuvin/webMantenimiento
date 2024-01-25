package uy.edu.bios.obligatorio.obligatoriospringboot.dominio;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

    @NotBlank
    @Size(max = 30)
    @Id
    @Column(name = "nombre_usuario", length = 30)
    private String nombreUsuario;

    @NotBlank
    @Size(max = 200)
    @Column(name = "pass",nullable = false, length = 200)
    private String password;

    @Transient
    private String repetirPassword;  

    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String nombre;
    
    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String apellido;
    
    @Size(max = 50)
    @Email
    @Column(length = 50)
    private String eMail; 

    private boolean activo;       

    @ManyToMany
    @JoinTable(joinColumns = { @JoinColumn(name = "usuario_nombre_usuario") }, inverseJoinColumns = { @JoinColumn(name = "rol_nombre_rol") })
    private Set<Rol> roles;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getRepetirPassword() {
        return repetirPassword;
    }

    public void setRepetirPassword(String repetirPassword) {
        this.repetirPassword = repetirPassword;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String geteMail() {
        return eMail;
    }
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isActivo() {
        return activo;
    }

    public Set<Rol> getRoles() {
        return this.roles;
    }

    public Usuario() {
        this(null, null, null, null, null, false);
    }

    public Usuario(String nombreUsuario, String password, String nombre, String apellido, String eMail, boolean activo) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.eMail = eMail;
        this.activo=activo;

        roles= new HashSet<>();
    }
    
}


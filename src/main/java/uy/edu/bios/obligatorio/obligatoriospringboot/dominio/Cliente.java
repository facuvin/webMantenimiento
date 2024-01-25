package uy.edu.bios.obligatorio.obligatoriospringboot.dominio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "nombre_usuario", referencedColumnName = "nombre_usuario")
public class Cliente extends Usuario {

    @NotNull
    @Positive
    @Column(nullable = false, unique = true)
    private Integer cedula;
    
    @NotBlank
    @Column(nullable = false, length = 100)
    private String direccion;
    
    @NotBlank
    @Column(nullable = false, length = 30, unique = true)
    private String telefono;

    private Integer horasTecnico;

    public Integer getCedula() {
        return cedula;
    }
    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getHorasTecnico() {
        return horasTecnico;
    }
    public void setHorasTecnico(Integer horasTecnico) {
        this.horasTecnico = horasTecnico;
    }

    public Cliente() {
        this(null, null, null, null, null, false, null, null, null, null);
    }
    public Cliente(String nombreUsu, String password, String nombre, String apellido, String eMail,boolean activo, Integer cedula,
            String direccion, String telefono,Integer horasTecnico) {
        super(nombreUsu, password, nombre, apellido, eMail, activo);
        this.cedula = cedula;
        this.direccion = direccion;
        this.telefono = telefono;
        this.horasTecnico=horasTecnico;
    } 
}

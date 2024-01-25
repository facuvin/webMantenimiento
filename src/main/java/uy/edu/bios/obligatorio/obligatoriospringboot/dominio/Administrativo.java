package uy.edu.bios.obligatorio.obligatoriospringboot.dominio;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "administrativos")
@PrimaryKeyJoinColumn(name = "nombre_usuario", referencedColumnName = "nombre_usuario")
public class Administrativo extends Usuario {

    
    @PastOrPresent
    private LocalDate fechaIngreso;


    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }


    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

   

    
    public Administrativo() {
        this(null, null, null, null, null, false, null);        
    }

    public Administrativo(String nombreUsu, String password, String nombre, String apellido, String eMail,boolean activo,
            LocalDate fechaIngreso) {
        super(nombreUsu, password, nombre, apellido, eMail, activo);
        this.fechaIngreso = fechaIngreso;
    }

    @Override
    public String toString() {
        return super.toString()+"Administrativo [fechaIngreso=" + fechaIngreso + "]";
    }

    
}

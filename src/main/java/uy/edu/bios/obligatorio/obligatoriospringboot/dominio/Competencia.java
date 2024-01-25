package uy.edu.bios.obligatorio.obligatoriospringboot.dominio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "competencias")
public class Competencia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Size(max= 100)
    @NotBlank
    @Column(nullable = false, length = 100, unique = true)
    private String nombre;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public Competencia(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public Competencia() {
        this(null, null);
    }

}

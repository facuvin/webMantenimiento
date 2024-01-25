package uy.edu.bios.obligatorio.obligatoriospringboot.controladores;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionNoExiste;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionYaExiste;

@ControllerAdvice(basePackageClasses = ControladorCompartido.class)
public class ControladorCompartido {

    @ExceptionHandler
    public String manejarExcepcion(Exception e, Model model) {
        model.addAttribute("mensaje", "No se pudo procesar la solicitud.");

        return "excepcion";
    }

    @ExceptionHandler
    public String manejarExcepcionNoExiste(ExcepcionNoExiste e, Model model) {
        model.addAttribute("mensaje", "No se pudo procesar la solicitud: " + e.getMessage());

        return "excepcion";
    }

    @ExceptionHandler
    public String manejarExcepcionYaExiste(ExcepcionYaExiste e, Model model) {
        model.addAttribute("mensaje", "No se pudo procesar la solicitud: " + e.getMessage());

        return "excepcion";
    }
    
}

package uy.edu.bios.obligatorio.obligatoriospringboot.controladores;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Competencia;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Tecnico;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.ISercivioCompetencias;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.IServicioTecnicos;

@Controller
@RequestMapping("/tecnicos")
public class ControladorTecnicos {

    @Autowired
    IServicioTecnicos servicioTecnicos;

    @Autowired
    ISercivioCompetencias sercivioCompetencias;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @GetMapping
    public String mostrarIndex(Model model, @RequestParam(required = false) String criterio){
        List<Tecnico> tecnicos = servicioTecnicos.buscar(criterio);
        List<Competencia> competencias = sercivioCompetencias.listar();
        model.addAttribute("competencias", competencias); 
        model.addAttribute("tecnicos", tecnicos); 
        return "administrar/tecnicos/index";
    }

    @GetMapping("/agregar")
    public String mostrarAgregar(@ModelAttribute Tecnico tecnico, Model model) {
        List<Competencia> competencias = sercivioCompetencias.listar();
        String agregar="Agregar Tecnico";
        model.addAttribute("competencias", competencias); 
        model.addAttribute("encabezadoForm", agregar);
        return "administrar/tecnicos/agregar";
    }

    @PostMapping("/agregar")
    public String procesarAgregar(@ModelAttribute @Valid Tecnico tecnico, BindingResult result, Model model, RedirectAttributes attributes) {
        List<Competencia> competencias = sercivioCompetencias.listar();
        model.addAttribute("competencias", competencias); 
        if (result.hasErrors()) {
            return "administrar/tecnicos/agregar";
        }
        try {
            tecnico.setPassword(passwordEncoder.encode(tecnico.getPassword()));
            servicioTecnicos.agregar(tecnico);
            attributes.addFlashAttribute("mensaje", "Tecnico agregado con éxito.");
            return "redirect:/tecnicos";

        } catch (ExcepcionBiosServiTutti e) {
            model.addAttribute("mensaje", "¡ERROR! " + e.getMessage());
            return "administrar/tecnicos/agregar";
        }
    }

    @GetMapping("/{nombreUsuario}")
    public String mostrarDetalle(@PathVariable("nombreUsuario") String nombreUsuario, Model model) {
        Tecnico tecnico = servicioTecnicos.obtener(nombreUsuario);

        if (tecnico != null) {
            model.addAttribute("tecnico", tecnico);
        } else {
            model.addAttribute("mensaje", "¡ERROR! No se encontró el tecnico con el nombre de usuario " + nombreUsuario + ".");
        }

        return "administrar/tecnicos/detalle";
    }

    @GetMapping("/modificar")
    public String mostrarModificar(String nombreUsuario, Model model) {
        Tecnico tecnico = servicioTecnicos.obtener(nombreUsuario);
        List<Competencia> competencias = sercivioCompetencias.listar();
        model.addAttribute("competencias", competencias); 

        if (tecnico != null) {
            model.addAttribute("tecnico", tecnico);
            model.addAttribute("contrasenaFalsa", UUID.randomUUID().toString());
        } else {
            model.addAttribute("mensaje", "¡ERROR! No se encontró el tecnico con el nombre de usuario " + nombreUsuario + ".");
        }

        return "administrar/tecnicos/modificar";
    }

    @PostMapping("/modificar")
    public String procesarModificar(@ModelAttribute @Valid Tecnico tecnico, BindingResult result, String contrasenaFalsa, Model model, RedirectAttributes attributes) {
        
        List<Competencia> competencias = sercivioCompetencias.listar();
        model.addAttribute("competencias", competencias); 
        
        if (result.hasErrors()) {
            model.addAttribute("contrasenaFalsa", UUID.randomUUID().toString());

            return "administrar/tecnicos/modificar";
        }

        Tecnico tecnicoDB = servicioTecnicos.obtener(tecnico.getNombreUsuario());

        try {
            if (contrasenaFalsa.equals(tecnico.getPassword())) {
                tecnico.setPassword(tecnicoDB.getPassword());
            } else {
               tecnico.setPassword(passwordEncoder.encode(tecnico.getPassword()));
            }

            servicioTecnicos.modificar(tecnico);

            attributes.addFlashAttribute("mensaje", "Tecnico modificado con éxito.");

            return "redirect:/tecnicos";
        } catch (ExcepcionBiosServiTutti e) {
            model.addAttribute("mensaje", "¡ERROR! " + e.getMessage());

            return "administrar/tecnicos/modificar";
        }
    }

    @GetMapping("/eliminar")
    public String mostrarEliminar(String nombreUsuario, Model model) {
        Tecnico tecnico = servicioTecnicos.obtener(nombreUsuario);


        if (tecnico != null && tecnico.isActivo()) {
            model.addAttribute("tecnico", tecnico);
        }else if(tecnico != null && !tecnico.isActivo()){
            model.addAttribute("mensaje", "¡ERROR! El tecnico" + nombreUsuario + " no se encuentra en actividad.");
        } 
        else {
            model.addAttribute("mensaje", "¡ERROR! No se encontró el tecnico con el nombre de usuario " + nombreUsuario + ".");
        }

        return "administrar/tecnicos/eliminar";
    }

    @PostMapping("/eliminar")
    public String procesarEliminar(String nombreUsuario, Model model, RedirectAttributes attributes) {
        try {
            servicioTecnicos.eliminar(nombreUsuario);

            attributes.addFlashAttribute("mensaje", "Tecnico eliminado con éxito.");

            return "redirect:/tecnicos";
        } catch (ExcepcionBiosServiTutti e) {
            model.addAttribute("mensaje", "¡ERROR! " + e.getMessage());

            return "administrar/tecnicos/eliminar";
        }
    }
}

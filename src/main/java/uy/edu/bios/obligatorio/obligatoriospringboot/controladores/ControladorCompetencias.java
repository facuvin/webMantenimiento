package uy.edu.bios.obligatorio.obligatoriospringboot.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Competencia;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.ISercivioCompetencias;


@Controller
@RequestMapping("/competencias")
public class ControladorCompetencias {

    @Autowired
    ISercivioCompetencias sercivioCompetencias;

    @GetMapping
    public String mostrarIndex(Model model, @RequestParam(required = false) String criterio) {
        List<Competencia> competencias = sercivioCompetencias.buscar(criterio);
        model.addAttribute("Competencia", competencias);
        return "competencias/index";
    }

    @GetMapping("/agregar")
    public String mostrarAgregar(@ModelAttribute Competencia Competencia) {
        return "competencias/agregar";
    }

    @PostMapping("/agregar")
    public String procesarAgregar(@ModelAttribute @Valid Competencia Competencia, BindingResult result, Model model,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            return "competencias/agregar";
        }

        try {

            Competencia.setId(0);
            sercivioCompetencias.agregar(Competencia);

            attributes.addFlashAttribute("mensaje", "Competencia agregada con éxito.");

            return "redirect:/competencias";
        } catch (ExcepcionBiosServiTutti e) {
            model.addAttribute("mensaje", "¡ERROR! " + e.getMessage());
            return "competencias/agregar";
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("mensaje", "¡ERROR! Ya existe una competencia con el nombre indicado");
            return "competencias/agregar";
        }
    }

    @GetMapping("/modificar")
    public String mostrarModificar(Integer id, Model model) {
        Competencia competencia = sercivioCompetencias.obtener(id);

        if (competencia != null) {
            model.addAttribute("competencia", competencia);
        } else {
            model.addAttribute("mensaje", "¡ERROR! No se encontró la competencia con el id " + id + ".");
        }

        return "competencias/modificar";
    }

    @PostMapping("/modificar")
    public String procesarModificar(@ModelAttribute @Valid Competencia competencia, BindingResult result, Model model, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            return "competencias/modificar";
        }

        try {
            sercivioCompetencias.modificar(competencia);

            attributes.addFlashAttribute("mensaje", "Competencia modificada con éxito.");

            return "redirect:/competencias";
        } catch (ExcepcionBiosServiTutti e) {
            model.addAttribute("mensaje", "¡ERROR! " + e.getMessage());

            return "competencias/modificar";
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("mensaje", "¡ERROR! Ya existe una competencia con el nombre indicado");
            return "competencias/modificar";
        }
    }

    @GetMapping("/eliminar")
    public String mostrarEliminar(Integer id, Model model) {
         Competencia competencia = sercivioCompetencias.obtener(id);


        if (competencia != null) {
            model.addAttribute("competencia", competencia);
        } else {
            model.addAttribute("mensaje", "¡ERROR! No se encontró la competencia con el numero de id " + id + ".");
        }

        return "competencias/eliminar";
    }

    @PostMapping("/eliminar")
    public String procesarEliminar(Integer id, Model model, RedirectAttributes attributes) {
        List<Competencia> competencias = sercivioCompetencias.listar();
        model.addAttribute("Competencia", competencias); 
        try {
            sercivioCompetencias.eliminar(id);

            attributes.addFlashAttribute("mensaje", "La competencia eliminada con éxito.");

            return "redirect:/competencias";
        } catch (ExcepcionBiosServiTutti e) {
            model.addAttribute("mensaje", "¡ERROR! "+ e.getMessage());

            return "competencias/index";
        }
    }




  












    }
    



package uy.edu.bios.obligatorio.obligatoriospringboot.controladores;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Cliente;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Competencia;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Tecnico;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Visita;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionNoExiste;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.ISercivioCompetencias;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.IServicioClientes;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.IServicioSolicitudes;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.IServicioTecnicos;

@Controller
@RequestMapping("/solicitudes")
public class ControladorSolicitudes {

    @Autowired
    IServicioSolicitudes servicioSolicitudes;

    @Autowired
    ISercivioCompetencias sercivioCompetencias;

    @Autowired
    IServicioClientes servicioClientes;

    @Autowired
    IServicioTecnicos servicioTecnicos;
    
    @GetMapping("/tecnico")
    public String listarSolicitudesPorTecnico(Principal principal, Model model) {
        Tecnico tecnico = servicioTecnicos.obtener(principal.getName());

        List<Visita> solicitudes = servicioSolicitudes.listarSolicitudesPorTecnico(tecnico);
        model.addAttribute("solicitudes", solicitudes);

        return "solicitudes/solicitudes-tecnico";
    }

    @GetMapping
    public String inicioSolicitudes(Model model){
        List<Visita> solicitudes = servicioSolicitudes.listar();
        model.addAttribute("solicitudes", solicitudes);

        return "solicitudes/index";
    }

     @GetMapping("/{id}")
    public String mostrarDetalle(@PathVariable("id") Integer id, Model model) {
        Visita visita = servicioSolicitudes.obtener(id);

        if (visita != null) {
          model.addAttribute("visita", visita);
          return "solicitudes/detalle";

        } else {
            model.addAttribute("mensaje", "¡ERROR! No se encontró la visita con el ID : " + id + ".");
        }

        return "solicitudes/detalle";
    }

    @GetMapping("/generar")
    public String generarVisita(Principal principal, Model model, @ModelAttribute Visita visita){
        List<Competencia> competencias= sercivioCompetencias.listar();
        Cliente cliente= servicioClientes.obtener(principal.getName());
        model.addAttribute("cliente", cliente);
        
        model.addAttribute("competencias", competencias);

        return "solicitudes/generar";
    }

    @PostMapping("/generar")
    public String procesarVisita(Principal principal, @ModelAttribute @Valid Visita visita, BindingResult result,
            Model model, RedirectAttributes attributes) throws ExcepcionNoExiste{

        List<Competencia> competencias = sercivioCompetencias.listar();
        model.addAttribute("competencias", competencias);

        if (result.hasErrors()) {
            model.addAttribute("competencias", competencias);
            return "solicitudes/generar";
        }
         Cliente cliente = servicioClientes.obtener(principal.getName());
            if (cliente == null) {
                throw new ExcepcionNoExiste("El nombre de usuario del Cliente no existe");
            }

            visita.setCliente(cliente);

        try {
            if (principal == null || principal instanceof AnonymousAuthenticationToken) {
            throw new ExcepcionBiosServiTutti("Debes ingresar a tu cuenta para poder Solicitar una visita.");
            }         

            servicioSolicitudes.registrarSolicitud(visita);

            attributes.addFlashAttribute("mensaje", "Solicitud enviada con éxito.");

            return "redirect:/solicitudes/generar";

        } catch (ExcepcionBiosServiTutti e) {
            model.addAttribute("competencias", competencias);
            model.addAttribute("mensaje", "¡ERROR! " + e.getMessage());
            return "solicitudes/generar";
        }
    }

    @GetMapping("/asignar/{id}")
    public String asignarTecnicoASolicitud(@PathVariable("id") Integer id, @RequestParam(name="nombre", required = false) String nombre, Model model){

        Visita visita= servicioSolicitudes.obtener(id);
        List<Tecnico> tecnicos=servicioTecnicos.listarActivos();
        model.addAttribute("tecnicos", tecnicos);
        model.addAttribute("visita", visita);

        return "solicitudes/asignar";
    }
    @GetMapping("/asignar/{id}/{nombre}")
    public String asignarATecnicoSolicitud(@PathVariable("id") Integer id, @PathVariable("nombre") String nombre, Model model){

        Visita visita= servicioSolicitudes.obtener(id);
        Tecnico tecnico=servicioTecnicos.obtener(nombre);
        model.addAttribute("visita", visita);
        model.addAttribute("tecnico", tecnico);

        return"solicitudes/confirmar";
    }
    @PostMapping("/asignar/{numero}/{nombreUsuario}")
    public String asignarTecnicoASolicitud(@ModelAttribute @Valid Visita visita, BindingResult resultV, @ModelAttribute @Valid Tecnico tecnico, BindingResult resultT, RedirectAttributes attributes, Model model){

        if (visita.getNumero()==null || tecnico.getNombreUsuario()==null) {
            return "redirect:/solicitudes";
        }        
        try {
            servicioSolicitudes.asignarTecnicoASolicitud(tecnico.getNombreUsuario(), visita.getNumero());
            attributes.addFlashAttribute("mensaje", "Asignacion con Exito.");
            return "redirect:/solicitudes";
        } catch (ExcepcionBiosServiTutti e) {
            model.addAttribute("mensaje", "¡ERROR! " + e.getMessage());
            return "solicitudes/index";
        }

    }


}









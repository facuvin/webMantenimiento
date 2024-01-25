package uy.edu.bios.obligatorio.obligatoriospringboot.controladores;

import java.util.List;
import java.util.UUID;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Administrativo;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.IServicioAdministrativos;

@Controller
@RequestMapping("/administrativos")
public class ControladorAdministrativos {

    @Autowired
    IServicioAdministrativos servicioAdministrativos;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping
    public String mostrarIndex(Model model, @RequestParam(required = false) String criterio){
        List<Administrativo> administrativos= servicioAdministrativos.buscar(criterio);
        model.addAttribute("administrativos", administrativos); 
        return "administrar/administrativos/index";
    }
    
    @GetMapping("/agregar")
    public String mostrarAgregar(@ModelAttribute Administrativo administrativo) {
        return "administrar/administrativos/agregar";
    }

    @PostMapping("/agregar")
    public String procesarAgregar(@ModelAttribute @Valid Administrativo administrativo, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "administrar/administrativos/agregar";
        }
        try {
            administrativo.setPassword(passwordEncoder.encode(administrativo.getPassword()));

            servicioAdministrativos.agregar(administrativo);

            attributes.addFlashAttribute("mensaje", "Administrativo agregado con éxito.");

            return "redirect:/administrativos";
        } catch (ExcepcionBiosServiTutti e) {
            model.addAttribute("mensaje", "¡ERROR! " + e.getMessage());

            return "administrar/administrativos/agregar";
        }

        
    }

     @GetMapping("/{nombreUsuario}")
    public String mostrarDetalle(@PathVariable("nombreUsuario") String nombreUsuario, Model model) {
        Administrativo administrativo = servicioAdministrativos.obtener(nombreUsuario);

        if (administrativo != null) {
            model.addAttribute("administrativo", administrativo);
        } else {
            model.addAttribute("mensaje", "¡ERROR! No se encontró el administrativo con el nombre de usuario " + nombreUsuario + ".");
        }

        return "administrar/administrativos/detalle";
    }

    @GetMapping("/modificar")
    public String mostrarModificar(String nombreUsuario, Model model) {
        Administrativo administrativo = servicioAdministrativos.obtener(nombreUsuario);

        if (administrativo != null) {
            model.addAttribute("administrativo", administrativo);
            model.addAttribute("contrasenaFalsa", UUID.randomUUID().toString());
        } else {
            model.addAttribute("mensaje", "¡ERROR! No se encontró el empleado con el nombre de usuario " + nombreUsuario + ".");
        }

        return "administrar/administrativos/modificar";
    }

    @PostMapping("/modificar")
    public String procesarModificar(@ModelAttribute @Valid Administrativo administrativo, BindingResult result, String contrasenaFalsa, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("contrasenaFalsa", UUID.randomUUID().toString());

            return "administrar/administrativos/modificar";
        }

        Administrativo administrativoDB = servicioAdministrativos.obtener(administrativo.getNombreUsuario());

        try {
            if (contrasenaFalsa.equals(administrativo.getPassword())) {
                administrativo.setPassword(administrativoDB.getPassword());
            } else {
               administrativo.setPassword(passwordEncoder.encode(administrativo.getPassword()));
            }

            servicioAdministrativos.modificar(administrativo);

            attributes.addFlashAttribute("mensaje", "Administrativo modificado con éxito.");

            return "redirect:/administrativos";
        } catch (ExcepcionBiosServiTutti e) {
            model.addAttribute("mensaje", "¡ERROR! " + e.getMessage());

            return "administrar/administrativos/modificar";
        }
    }

    @GetMapping("/eliminar")
    public String mostrarEliminar(Principal principal, String nombreUsuario, Model model) {
        Administrativo administrativo = servicioAdministrativos.obtener(nombreUsuario);


        if (administrativo != null) {
            model.addAttribute("administrativo", administrativo);
        } else {
            model.addAttribute("mensaje", "¡ERROR! No se encontró el administrativo con el nombre de usuario " + nombreUsuario + ".");
        }

        return "administrar/administrativos/eliminar";
    }

    @PostMapping("/eliminar")
    public String procesarEliminar(HttpServletRequest request, String nombreUsuario, Model model, RedirectAttributes attributes, Principal principal) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            

            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {                
                throw new ExcepcionBiosServiTutti("debes estar logueado para eliminar");
            }
            
            if(authentication.getName() != nombreUsuario){
                new SecurityContextLogoutHandler().logout(request, null, null);
                SecurityContextHolder.getContext().setAuthentication(null);
                servicioAdministrativos.eliminar(nombreUsuario);
                return "redirect:/";
            }
            servicioAdministrativos.eliminar(nombreUsuario);
            attributes.addFlashAttribute("mensaje", "Administrativo eliminado con éxito.");

            return "redirect:/administrativos";
        } catch (ExcepcionBiosServiTutti e) {
            model.addAttribute("mensaje", "¡ERROR! " + e.getMessage());

            return "administrar/administrativos/eliminar";
        }
    }
}

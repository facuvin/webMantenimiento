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
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Cliente;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.IServicioClientes;

@Controller
@RequestMapping("/clientes")
public class ControladorClientes {

    @Autowired
    IServicioClientes servicioClientes;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping
    public String mostrarIndex(Model model, @RequestParam(required = false) String criterio) {
        List<Cliente> clientes = servicioClientes.buscar(criterio);
        model.addAttribute("clientes", clientes);

        return "administrar/clientes/index";
    }

    @GetMapping("/registrar")
    public String mostrarAgregar(@ModelAttribute Cliente cliente) {

        return "administrar/clientes/registrar";
    }

    @PostMapping("/registrar")
    public String procesarAgregar(@ModelAttribute @Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "administrar/clientes/registrar";
        }
        try {
            cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));

            servicioClientes.agregar(cliente);

            attributes.addFlashAttribute("mensaje", "Cliente agregado con éxito.");

            return "redirect:/clientes";
        } catch (ExcepcionBiosServiTutti e) {
            model.addAttribute("mensaje", "¡ERROR! " + e.getMessage());

            return "administrar/clientes/registrar";
        }        
    }

    @GetMapping("/{nombreUsuario}")
    public String mostrarDetalle(@PathVariable("nombreUsuario") String nombreUsuario, Model model) {
        Cliente cliente = servicioClientes.obtener(nombreUsuario);

        if (cliente != null) {
            model.addAttribute("cliente", cliente);
        } else {
            model.addAttribute("mensaje", "¡ERROR! No se encontró el cliente con el nombre de usuario " + nombreUsuario + ".");
        }

        return "administrar/clientes/detalle";
    }

    @GetMapping("/modificar")
    public String mostrarModificar(String nombreUsuario, Model model) {
        Cliente cliente = servicioClientes.obtener(nombreUsuario);

        if (cliente != null) {
            model.addAttribute("cliente", cliente);
            model.addAttribute("contrasenaFalsa", UUID.randomUUID().toString());
        } else {
            model.addAttribute("mensaje", "¡ERROR! No se encontró el empleado con el nombre de usuario " + nombreUsuario + ".");
        }

        return "administrar/clientes/modificar";
    }

    @PostMapping("/modificar")
    public String procesarModificar(@ModelAttribute @Valid Cliente cliente, BindingResult result, String contrasenaFalsa, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("contrasenaFalsa", UUID.randomUUID().toString());

            return "administrar/clientes/modificar";
        }

        Cliente clienteBD = servicioClientes.obtener(cliente.getNombreUsuario());

        try {
            if (contrasenaFalsa.equals(cliente.getPassword())) {
                cliente.setPassword(clienteBD.getPassword());
            } else {
               cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
            }

            servicioClientes.modificar(cliente);

            attributes.addFlashAttribute("mensaje", "Cliente modificado con éxito.");

            return "redirect:/clientes";
        } catch (ExcepcionBiosServiTutti e) {
            model.addAttribute("mensaje", "¡ERROR! " + e.getMessage());

            return "administrar/clientes/modificar";
        }
    }
    
}

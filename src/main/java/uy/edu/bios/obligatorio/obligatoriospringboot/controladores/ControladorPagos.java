package uy.edu.bios.obligatorio.obligatoriospringboot.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Cliente;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Pago;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionNoExiste;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.IServicioClientes;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.IServicioPagos;


@Controller
@RequestMapping("/pagos")
public class ControladorPagos {
    @Autowired
    IServicioPagos servicioPagos;

    @Autowired
    IServicioClientes servicioClientes;
    
    @GetMapping("/cliente/{nombreCliente}")
    public String verPagosCliente(@PathVariable String nombreCliente, Model model) throws ExcepcionNoExiste {
        Cliente cliente = servicioClientes.obtener(nombreCliente);
        if (cliente == null) {
            throw new ExcepcionNoExiste("El cliente indicado no existe");
        }

        List<Pago> pagos = servicioPagos.listarPagos(cliente);

        model.addAttribute("nombreCliente", nombreCliente);
        model.addAttribute("pagos", pagos);

        return "pagos/pagos-cliente";
    }

    @GetMapping("/detalle/{numeroPago}")
    public String mostrarDetalle(@PathVariable("numeroPago") Integer numeroPago, Model model) {
        Pago pago = servicioPagos.obtener(numeroPago);

        if (pago != null) {
            model.addAttribute("pago", pago);
        } else {
            model.addAttribute("mensaje", "¡ERROR! No se encontró el pago con el número " + numeroPago + ".");
        }

        return "pagos/detalle";
    }

    
    @GetMapping("/registrar/{nombreCliente}")
    public String mostrarAgregar(@ModelAttribute Pago pago, @PathVariable String nombreCliente, Model model) {
        model.addAttribute("nombreCliente", nombreCliente);
        
        return "pagos/agregar";
    }

    @PostMapping("/registrar/{nombreCliente}")
    public String procesarAgregar(@ModelAttribute @Valid Pago pago,BindingResult result, @PathVariable String nombreCliente,  Model model, RedirectAttributes attributes) throws ExcepcionNoExiste {
        if (result.hasErrors()) {
            return "pagos/agregar";
        }
        Cliente cliente = servicioClientes.obtener(nombreCliente);
        if (cliente == null) {
            throw new ExcepcionNoExiste("El nombre de usuario del Cliente no existe");
        }

        pago.setCliente(cliente);

        try {
            servicioPagos.registrarPago(pago);

            attributes.addFlashAttribute("mensaje", "Pago registrado con éxito.");

            return "redirect:/clientes";
        } catch (ExcepcionBiosServiTutti e) {
            model.addAttribute("mensaje", "¡ERROR! " + e.getMessage());

            return "pagos/agregar";
        }
        
    }
}

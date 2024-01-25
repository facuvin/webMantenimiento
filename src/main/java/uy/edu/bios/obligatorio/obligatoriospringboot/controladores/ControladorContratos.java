package uy.edu.bios.obligatorio.obligatoriospringboot.controladores;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.CancelacionContrato;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Cliente;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Contrato;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionNoExiste;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.IServicioClientes;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.IServicioContratos;
import uy.edu.bios.obligatorio.obligatoriospringboot.utilidades.UtilidadesArchivos;

@Controller
@RequestMapping("/contratos")
public class ControladorContratos {
    
    @Value("${lcr.ruta-imagenes-contratos}")
    private String rutaImagenesContratos;

    @Autowired
    IServicioContratos servicioContratos;

    @Autowired
    IServicioClientes servicioClientes;

    @GetMapping("/cliente/{nombreCliente}")
    public String verContratosCliente(@PathVariable String nombreCliente, Model model) throws ExcepcionNoExiste {
        Cliente cliente = servicioClientes.obtener(nombreCliente);
        if (cliente == null) {
            throw new ExcepcionNoExiste("El cliente indicado no existe");
        }

        List<Contrato> contratos = servicioContratos.listarContratosCliente(cliente);

        for (Contrato c : contratos) {
            if (c.getCancelacion() == null) {
                model.addAttribute("activo", true);
            }
        }

        model.addAttribute("nombreCliente", nombreCliente);
        model.addAttribute("contratos", contratos);

        return "contratos/contratos-cliente";
    }

    @GetMapping("/detalle/{numeroContrato}")
    public String mostrarDetalle(@PathVariable("numeroContrato") Integer numeroContrato, Model model) {
        Contrato contrato = servicioContratos.obtener(numeroContrato);

        if (contrato != null) {
            model.addAttribute("contrato", contrato);
        } else {
            model.addAttribute("mensaje", "¡ERROR! No se encontró el contrato con el número " + numeroContrato + ".");
        }

        return "contratos/detalle";
    }


    @GetMapping("/agregar/{nombreCliente}")
    public String mostrarAgregar(@ModelAttribute Contrato contrato, Model model, @PathVariable String nombreCliente) {

        model.addAttribute("nombreCliente", nombreCliente);

        return "contratos/agregar";
    }

    @PostMapping("/agregar/{nombreCliente}")
    public String procesarAgregar(@ModelAttribute @Valid Contrato contrato, BindingResult result, @PathVariable String nombreCliente, MultipartFile imagen, Model model, RedirectAttributes attributes) throws ExcepcionNoExiste {

        if (result.hasErrors()) {
            return "contratos/agregar";
        }

        Cliente cliente = servicioClientes.obtener(nombreCliente);
        if (cliente == null) {
            throw new ExcepcionNoExiste("El nombre de usuario del Cliente no existe");
        }

        contrato.setCliente(cliente);

        try {
            if (!imagen.isEmpty()) {
                UtilidadesArchivos.guardarComoImagen(imagen.getBytes(), rutaImagenesContratos, contrato.getNumero().toString(), "png");

                contrato.setTieneImagen(true);
            }

            servicioContratos.agregarContrato(contrato);

            attributes.addFlashAttribute("mensaje", "Contrato registrado con éxito.");

            return "redirect:/contratos/cliente/{nombreCliente}";
        } catch (MaxUploadSizeExceededException | IOException e) {
            model.addAttribute("mensaje", "¡ERROR! No se pudo subir la imagen del contrato.");

            return "contratos/agregar";
        } catch (ExcepcionBiosServiTutti e) {
            model.addAttribute("mensaje", "¡ERROR! " + e.getMessage());

            return "contratos/agregar";
        }
    }

    @GetMapping("/cancelar/{numeroContrato}")
    public String mostrarCancelar(@ModelAttribute(name = "cancelacion") CancelacionContrato cancelacion, @PathVariable Integer numeroContrato, Model model) {

        return "contratos/cancelar";
    }

    @PostMapping("/cancelar/{numeroContrato}")
    public String procesarCancelar(@ModelAttribute(name = "cancelacion") @Valid CancelacionContrato cancelacion, BindingResult result, @PathVariable Integer numeroContrato, Model model, RedirectAttributes attributes){

        model.addAttribute("numeroContrato", numeroContrato);
        if (result.hasErrors()) {
            return "contratos/cancelar";
        }

        try {     

        Contrato contrato = servicioContratos.obtener(numeroContrato);
        if (contrato == null) {
            throw new ExcepcionNoExiste("El numero de contrato no existe");
        }

        cancelacion.setContrato(contrato);

        servicioContratos.cancelarContrato(cancelacion);
        attributes.addFlashAttribute("mensaje", "Contrato cancelado con éxito.");
        
        return "redirect:/clientes";
        } catch (ExcepcionBiosServiTutti e) {
            model.addAttribute("mensaje", "¡ERROR! "+e.getMessage());
            return "contratos/cancelar";
        }
        
    }
}

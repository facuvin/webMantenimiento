package uy.edu.bios.obligatorio.obligatoriospringboot.controladores;

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
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Informe;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Visita;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.IServicioInformes;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.IServicioSolicitudes;

@Controller
@RequestMapping("/informes")
public class ControladorInformes {

    @Autowired
    IServicioInformes servicioInformes;

    @Autowired
    IServicioSolicitudes servicioSolicitudes;
    
    @GetMapping("/registrar/{numero}")
    public String registrarInforme(@ModelAttribute Informe informe, @PathVariable Integer numero, Model model){
        model.addAttribute("numero", numero);       
        return "informes/registrar";
    }

    @PostMapping("/registrar/{numero}")
    public String registrarInforme(@ModelAttribute @Valid Informe informe, BindingResult result,@PathVariable Integer numero, Model model, RedirectAttributes attributes){

        if (result.hasErrors()) {
            return "informes/registrar";
        }
        Visita visita = servicioSolicitudes.obtener(numero);
        informe.setVisita(visita);

        try {
            servicioInformes.registrarInforme(informe);
            if(informe.getHorasExtra()>0){
                attributes.addFlashAttribute("mensaje", "¡Informe realizado con exito."+'\n'
                +"Horas exedidas, debe cobrar: "+informe.getHorasExtra()+"Hs.");
            }else{
                 attributes.addFlashAttribute("mensaje", "Informe realizado con éxito.");
            }
            return "redirect:/solicitudes/tecnico";
        } catch (ExcepcionBiosServiTutti e) {
            model.addAttribute("mensaje", "¡ERROR! " + e.getMessage());
            return "informes/registrar";
        }

    }

    @GetMapping("/{id}")
    public String mostrarDetalledeinforme(@PathVariable("id") Integer id, Model model) {
        Informe informe = servicioInformes.obtener(id);

        if (informe != null) {
            model.addAttribute("informe", informe);
            return "informes/detalle";

        } else {
            model.addAttribute("mensaje", "¡ERROR! No se encontró el informe con el ID : " + id + ".");
        }

        return "solicitudes/detalle";
    }
    
}


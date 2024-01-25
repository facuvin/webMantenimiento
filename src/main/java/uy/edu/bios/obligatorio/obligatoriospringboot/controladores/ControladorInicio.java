package uy.edu.bios.obligatorio.obligatoriospringboot.controladores;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Controller
@RequestMapping("/")
public class ControladorInicio {

    @GetMapping
    public String inicio(){
        
        return "inicio/index";
    }

    @GetMapping("/login")
    public String ingresar(Principal principal) {
        if (principal == null|| principal instanceof AnonymousAuthenticationToken ) {
            return "inicio/ingresar";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String salir(HttpServletRequest request) {
        new SecurityContextLogoutHandler().logout(request, null, null);
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirct:/";
    }
    
}
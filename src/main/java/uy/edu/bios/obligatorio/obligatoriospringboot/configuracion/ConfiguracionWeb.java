package uy.edu.bios.obligatorio.obligatoriospringboot.configuracion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfiguracionWeb implements WebMvcConfigurer {

    @Value("${lcr.ruta-imagenes-contratos}")
    private String rutaImagenesContratos;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/imagenes-contratos/**").addResourceLocations("file:" + rutaImagenesContratos + "/");
    }

}

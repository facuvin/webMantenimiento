package uy.edu.bios.obligatorio.obligatoriospringboot.servicios.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Cliente;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Informe;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;
import uy.edu.bios.obligatorio.obligatoriospringboot.repositorios.IRepositorioClientes;
import uy.edu.bios.obligatorio.obligatoriospringboot.repositorios.IRepositorioInformes;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.IServicioInformes;

@Service
public class ServicioInformes implements IServicioInformes {

    @Autowired
    IRepositorioInformes repositorioInformes;

    @Autowired
    IRepositorioClientes repositorioClientes;

    @Override
    public void registrarInforme(Informe informe) throws ExcepcionBiosServiTutti {

        Integer horasRestantes, horasExtra, horasInforme;
        Cliente cli= informe.getVisita().getCliente();

        horasRestantes=cli.getHorasTecnico();
        horasInforme=informe.getDuracion();
        horasExtra=horasInforme-horasRestantes;
        if(horasExtra>0){
            repositorioClientes.setearHoras(0, cli.getNombreUsuario());
        }
        informe.setHorasExtra(horasExtra);
        repositorioInformes.save(informe);
    }

    @Override
    public Informe obtener(Integer numeroInforme) {

        Informe informe = repositorioInformes.findById(numeroInforme).orElse(null);

        return informe;
    }
    
}

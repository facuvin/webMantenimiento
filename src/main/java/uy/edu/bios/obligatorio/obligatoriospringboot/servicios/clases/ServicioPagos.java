package uy.edu.bios.obligatorio.obligatoriospringboot.servicios.clases;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Cliente;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Pago;
import uy.edu.bios.obligatorio.obligatoriospringboot.dominio.Contrato;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionBiosServiTutti;
import uy.edu.bios.obligatorio.obligatoriospringboot.excepciones.ExcepcionNoExiste;
import uy.edu.bios.obligatorio.obligatoriospringboot.repositorios.IRepositorioClientes;
import uy.edu.bios.obligatorio.obligatoriospringboot.repositorios.IRepositorioContratos;
import uy.edu.bios.obligatorio.obligatoriospringboot.repositorios.IRepositorioPagos;
import uy.edu.bios.obligatorio.obligatoriospringboot.servicios.interfaces.IServicioPagos;

@Service
public class ServicioPagos implements IServicioPagos {

    @Autowired
    IRepositorioPagos repositorioPagos;

    @Autowired
    IRepositorioContratos repositorioContratos;

    @Autowired
    IRepositorioClientes repositorioClientes;

    @Override
    public void registrarPago(Pago pago) throws ExcepcionBiosServiTutti {
        List<Pago> pagos=this.listarPagos(pago.getCliente());
        Cliente cli=pago.getCliente();
        List<Contrato> contratos=repositorioContratos.contratosCliente(cli.getNombreUsuario());
        boolean bol=false;

        for (Pago p : pagos) {
            if (p.getFecha().isAfter(LocalDate.now().minusDays(30))) {
                throw new ExcepcionBiosServiTutti("El cliente realizo un pago el dia: "+p.getFecha());
            }
        }

        for(Contrato c:contratos){
            if(c.getCancelacion()==null){
                pago.setFecha(LocalDate.now());
                repositorioClientes.setearHoras(10, cli.getNombreUsuario());                
                repositorioPagos.save(pago);
                bol=true;
            }
        }
        if(!bol)throw new ExcepcionNoExiste("El cliente no mantiene contrato activo.");
    }

    @Override
    public List<Pago> listarPagos(Cliente cliente) {
        return repositorioPagos.listar(cliente.getNombreUsuario());
    }

    @Override
    public Pago obtener(Integer numero) {
        return repositorioPagos.findById(numero).orElse(null);
    }
    
}

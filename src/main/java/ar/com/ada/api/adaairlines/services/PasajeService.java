package ar.com.ada.api.adaairlines.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.adaairlines.entities.Pasaje;
import ar.com.ada.api.adaairlines.entities.Reserva;
import ar.com.ada.api.adaairlines.entities.Reserva.EstadoReservaEnum;
import ar.com.ada.api.adaairlines.repos.PasajeRepository;

@Service
public class PasajeService {

    @Autowired
    PasajeRepository repo;

    @Autowired
    ReservaService resService;

    @Autowired
    VueloService vueloService;

    public Pasaje emitir(Integer reservaId) {

        Pasaje pasaje = new Pasaje();
        pasaje.setFechaEmision(new Date());
       // pasaje.setInfoPago("PAGADO");
        Reserva reserva = resService.buscarPorId(reservaId);
        reserva.setEstadoReservaId(EstadoReservaEnum.EMITIDA);
        reserva.setPasaje(pasaje);
        Integer nuevaCapacidad = reserva.getVuelo().getCapacidad() - 1;
        reserva.getVuelo().setCapacidad(nuevaCapacidad);

        /* problema concurrencia
         * "update vuelo set capacidad = 29 where vueloid = 99 and capacidad = 30"
         * 
         * "update vuelo set capacidad = 29 where vueloid = 99 and capacidad = 30"
         */

        vueloService.actualizar(reserva.getVuelo());
        // reservaService.actualizar(reserva);
        // pasajeService.actualizar(pasa);

        return pasaje;

    }

    public List<Pasaje> obtenerTodos() {
        return repo.findAll();
    }


    public boolean validarPasajeExiste(Integer id) {
        Pasaje pasaje = repo.findByPasajeId(id);
        if (pasaje != null) {
            return true;
        } else
        return false;
    }



    public Pasaje buscarPorId(Integer id) {
        return repo.findByPasajeId(id);
    }



    public enum ValidacionPasajeDataEnum {
        OK, ERROR_CAPACIDAD_MAXIMA_ALCANZADA, ERROR_RESERVA_NO_EXISTE, ERROR_RESERVA_YA_TIENE_UN_PASAJE
    }



    public ValidacionPasajeDataEnum validar(Integer reservaId) {
        Reserva reserva = resService.buscarPorId(reservaId);

        if (!resService.validarReservaExiste(reservaId))
            return ValidacionPasajeDataEnum.ERROR_RESERVA_NO_EXISTE;

        if (!validadCapacidadDisponible(reserva.getVuelo().getCapacidad()))
            return ValidacionPasajeDataEnum.ERROR_CAPACIDAD_MAXIMA_ALCANZADA;

        if(!validarReservaYaTieneUnPasajeAsignado(reservaId))
            return ValidacionPasajeDataEnum.ERROR_RESERVA_YA_TIENE_UN_PASAJE;

        return ValidacionPasajeDataEnum.OK;
    }




    private boolean validarReservaYaTieneUnPasajeAsignado(Integer reservaId) {
        return false;
    }



    public Pasaje modificarPasaje(Integer id, Integer reservaId) {
        Pasaje pasaje = buscarPorId(id);
        pasaje.setFechaEmision(new Date());

        Reserva reserva = resService.buscarPorId(reservaId);
        reserva.setEstadoReservaId(EstadoReservaEnum.EMITIDA);
        reserva.setPasaje(pasaje);
        Integer nuevaCapacidad = reserva.getVuelo().getCapacidad() - 1;

        if (validadCapacidadDisponible(reserva.getVuelo().getCapacidad())) {
            reserva.getVuelo().setCapacidad(nuevaCapacidad);

        } else {
            reserva.getVuelo().setCapacidad(null);
        }

        vueloService.actualizar(reserva.getVuelo());
        return repo.save(pasaje);
    }



    private boolean validadCapacidadDisponible(Integer capacidad) {
        return false;
    }

    public void eliminarPasajePorId(Integer id) {
        repo.deleteById(id);
    }
    
}

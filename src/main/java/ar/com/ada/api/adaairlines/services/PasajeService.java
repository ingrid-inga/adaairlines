package ar.com.ada.api.adaairlines.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.ada.api.adaairlines.entities.Pasaje;
import ar.com.ada.api.adaairlines.entities.Reserva;
import ar.com.ada.api.adaairlines.entities.Reserva.EstadoReservaEnum;
import ar.com.ada.api.adaairlines.repos.PasajeRepository;

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

        Reserva reserva = resService.buscarPorId(reservaId);
        reserva.setEstadoReservaId(EstadoReservaEnum.EMITIDA);
        reserva.asociarPasaje(pasaje);
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
    
}

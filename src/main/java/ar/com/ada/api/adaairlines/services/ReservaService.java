package ar.com.ada.api.adaairlines.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.ada.api.adaairlines.entities.*;
import ar.com.ada.api.adaairlines.entities.Reserva.EstadoReservaEnum;
import ar.com.ada.api.adaairlines.repos.ReservaRepository;

public class ReservaService {

    @Autowired
    ReservaRepository repo;

    @Autowired
    VueloService vueloService;

    @Autowired
    PasajeroService pasajeroService;

    public Integer generarReserva(Object vueloId, Integer pasajeroId) {

        Reserva reserva = new Reserva();

        Vuelo vuelo = vueloService.buscarPorId(vueloId);

        reserva.setFechaEmision(new Date());

        Calendar c = Calendar.getInstance();
        c.setTime(reserva.getFechaEmision());
        c.add(Calendar.DATE, 1);

        reserva.setFechaVencimiento(c.getTime());

        reserva.setEstadoReservaId(EstadoReservaEnum.CREADA);

        Pasajero pasajero = pasajeroService.buscarPorId(pasajeroId);

        // Relaciones bidireccionales.
        pasajero.agregarReserva(reserva);
        vuelo.agregarReserva(reserva);

        repo.save(reserva);
        return reserva.getReservaId();
    }

}
package ar.com.ada.api.adaairlines.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.adaairlines.entities.Reserva;
import ar.com.ada.api.adaairlines.entities.Usuario;
import ar.com.ada.api.adaairlines.models.request.EstadoReservaRequest;
import ar.com.ada.api.adaairlines.models.request.InfoReservaNueva;
import ar.com.ada.api.adaairlines.models.response.*;
import ar.com.ada.api.adaairlines.services.*;
import ar.com.ada.api.adaairlines.services.ReservaService.ValidacionReservaDataEnum;

@RestController
public class ReservaController {
    @Autowired
    ReservaService service;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/api/reservas")
    public ResponseEntity<GenericResponse> generarReserva(@RequestBody InfoReservaNueva infoReserva) {
        GenericResponse rta = new GenericResponse();

        // Obtengo a quien esta autenticado del otro lado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // De lo que esta autenticado, obtengo su USERNAME
        String username = authentication.getName();

        // Buscar el usuario por username
        Usuario usuario = usuarioService.buscarPorUsername(username);

        // con el usuario, obtengo el pasajero, y con ese, obtengo el Id
        Integer numeroReserva = service.generarReserva(infoReserva.vueloId, usuario.getPasajero().getPasajeroId());

        rta.id = numeroReserva;
        rta.isOk = true;
        rta.message = "Reserva creada";

        return ResponseEntity.ok(rta);
    }



    @GetMapping("/api/reservas")
    public ResponseEntity<List<Reserva>> traerReservas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }



    @GetMapping("/api/reservas/{id}")
    public ResponseEntity<?> traerReservasPorId(@PathVariable Integer id) {
        GenericResponse respuesta = new GenericResponse();
        if (!service.validarReservaExiste(id)) {
            respuesta.isOk = false;
            respuesta.message = "El Id de la Reserva no es válido";
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(service.buscarPorId(id));
    }


    
    @PutMapping("api/reservas/{id}")
    public ResponseEntity<?> modificar(@PathVariable Integer id, @RequestBody InfoReservaNueva infoNueva) {

        ReservaResponse respuesta = new ReservaResponse();

        ValidacionReservaDataEnum resultado = service.validar(infoNueva.vueloId);
        if (resultado == ValidacionReservaDataEnum.OK) {
            Reserva reserva = service.modificarReserva(id);

            respuesta.id = reserva.getReservaId();
            respuesta.fechaDeEmision = reserva.getFechaEmision();
            respuesta.fechaDeVencimiento = reserva.getFechaVencimiento();
            respuesta.message = " La reserva ha sido moficada con éxito";

            return ResponseEntity.ok(respuesta);
        } else {
            GenericResponse rta = new GenericResponse();
            rta.isOk = false;
            rta.message = "Error(" + resultado.toString() + ")";

            return ResponseEntity.badRequest().body(rta);
        }
    }

    @DeleteMapping("/api/reservas/{id}")
    public ResponseEntity<GenericResponse> eliminar(@PathVariable Integer id) {

        GenericResponse respuesta = new GenericResponse();
        if (service.validarReservaExiste(id)) {
            service.eliminarReservaPorId(id);
            respuesta.isOk = true;
            respuesta.message = "La reserva ha sido eliminada correctamente.";
            return ResponseEntity.ok(respuesta);

        } else {
            respuesta.isOk = false;
            respuesta.message = "El número de Id ingresado no es correcto.";
            return ResponseEntity.badRequest().body(respuesta);
        }
    }

    @PutMapping("/api/reservas/{id}/estados")
    public ResponseEntity<GenericResponse> putActualizarEstadoReserva(@PathVariable Integer id,
            @RequestBody EstadoReservaRequest estadoReserva) {

        GenericResponse r = new GenericResponse();

        Reserva reserva = service.buscarPorId(id);
        reserva.setEstadoReservaId(estadoReserva.estado);
        service.actualizar(reserva);

        r.isOk = true;
        r.message = "El estado de la Reserva ha sido actualizado";
        r.id = reserva.getReservaId();

        return ResponseEntity.ok(r);
    }
}

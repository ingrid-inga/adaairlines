package ar.com.ada.api.adaairlines.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.adaairlines.entities.Usuario;
import ar.com.ada.api.adaairlines.models.request.InfoReservaNueva;
import ar.com.ada.api.adaairlines.models.response.GenericResponse;
import ar.com.ada.api.adaairlines.services.*;

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

}

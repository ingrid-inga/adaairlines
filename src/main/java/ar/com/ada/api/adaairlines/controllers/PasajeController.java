package ar.com.ada.api.adaairlines.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.adaairlines.entities.Pasaje;
import ar.com.ada.api.adaairlines.models.request.InfoPasajeNuevo;
import ar.com.ada.api.adaairlines.models.response.GenericResponse;
import ar.com.ada.api.adaairlines.models.response.PasajeResponse;
import ar.com.ada.api.adaairlines.services.PasajeService;
import ar.com.ada.api.adaairlines.services.PasajeService.ValidacionPasajeDataEnum;

@RestController
public class PasajeController {

    @Autowired
    PasajeService service;


    @PostMapping("api/pasajes")
    public ResponseEntity<GenericResponse> emitir(@RequestBody InfoPasajeNuevo infoPasajes) {

        GenericResponse respuesta = new GenericResponse();

        Pasaje pasaje = service.emitir(infoPasajes.reservaId);

        respuesta.message = "El pasaje ha sido generado correctamente.";
        respuesta.isOk = true;
        respuesta.id = pasaje.getPasajeId();

        return ResponseEntity.ok(respuesta);
    }


    @GetMapping("/api/pasajes")
    public ResponseEntity<List<Pasaje>> traerPasajes() {
        return ResponseEntity.ok(service.obtenerTodos());
    }


    @GetMapping("api/pasajes/{id}")
    public ResponseEntity<?> traerPasajePorId(@PathVariable Integer id) {
        GenericResponse respuesta = new GenericResponse();
        if (!service.validarPasajeExiste(id)) {
            respuesta.isOk = false;
            respuesta.message = "El número de Id ingresado no es correcto.";
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(service.buscarPorId(id));
    }


    @PutMapping("/api/pasajes/{id}")
    public ResponseEntity<?> modificar(@PathVariable Integer id, @RequestBody InfoPasajeNuevo infoNueva) {

        PasajeResponse respuesta = new PasajeResponse();

        ValidacionPasajeDataEnum resultado = service.validar(infoNueva.reservaId);

        if (resultado == ValidacionPasajeDataEnum.OK) {

            Pasaje pasaje = service.modificarPasaje(id, infoNueva.reservaId);
            
                respuesta.pasajeId = pasaje.getPasajeId();
                respuesta.fechaDeEmision = pasaje.getFechaEmision();
                respuesta.reservaId = pasaje.getReserva().getReservaId();
                respuesta.vueloId = pasaje.getReserva().getVuelo().getVueloId();
                respuesta.infoDePago = "PAGADO";
                respuesta.message = "El pasaje ha sido modificado correctamente.";

                return ResponseEntity.ok(respuesta);
            
        }else {
            GenericResponse rta = new GenericResponse();
            rta.isOk = false;
            rta.message = "Error(" + resultado.toString() + ")";

            return ResponseEntity.badRequest().body(rta);
        }
    }
    

    @DeleteMapping("/api/pasajes/{id}")
    public ResponseEntity<GenericResponse> eliminar(@PathVariable Integer id) {

        GenericResponse respuesta = new GenericResponse();
        if (!service.validarPasajeExiste(id)) {
            service.eliminarPasajePorId(id);
            respuesta.isOk = true;
            respuesta.message = "El aeropuerto ha sido eliminado correctamente.";
            return ResponseEntity.ok(respuesta);

        } else {
            respuesta.isOk = false;
            respuesta.message = "El número de Id ingresado no es correcto.";
            return ResponseEntity.badRequest().body(respuesta);

        }

    }


    
}

package ar.com.ada.api.adaairlines.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.adaairlines.entities.Aeropuerto;
import ar.com.ada.api.adaairlines.models.response.GenericResponse;
import ar.com.ada.api.adaairlines.services.AeropuertoService;
import ar.com.ada.api.adaairlines.services.AeropuertoService.ValidacionAeropuertoDataEnum;

@RestController
public class AeropuertoController { // 21:23 el post solo crea la respuesta, el service crea el aeropuerto

    @Autowired
    AeropuertoService service;

    @PostMapping("/api/aeropuertos")
    public ResponseEntity<GenericResponse> crear(@RequestBody Aeropuerto aeropuerto) {

        GenericResponse respuesta = new GenericResponse();

        ValidacionAeropuertoDataEnum resultado = service.validar(aeropuerto);

        if (resultado == ValidacionAeropuertoDataEnum.OK) {
            service.crear(aeropuerto.getAeropuertoId(), aeropuerto.getNombre(), aeropuerto.getCodigoIATA());

            respuesta.isOk = true;
            respuesta.message = "Se creó el aeropuerto correctamente";
            respuesta.id = aeropuerto.getAeropuertoId();

            return ResponseEntity.ok(respuesta);
        } else {
            respuesta.isOk = false;
            respuesta.message = "Error(" + resultado.toString() + ")";

            return ResponseEntity.badRequest().body(respuesta);
        }
    }

    @GetMapping("/api/aeropuertos")
    public ResponseEntity<List<Aeropuerto>> traerAeropuertos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @DeleteMapping("/api/aeropuertos/{id}")
    public ResponseEntity<GenericResponse> eliminar(@PathVariable Integer id) {

        GenericResponse respuesta = new GenericResponse();
        if (service.validarAeropuertoExiste(id)) {
            service.eliminarAeropuertoPorId(id);
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

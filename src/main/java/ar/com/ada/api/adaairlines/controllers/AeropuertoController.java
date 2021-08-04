package ar.com.ada.api.adaairlines.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.adaairlines.entities.Aeropuerto;
import ar.com.ada.api.adaairlines.models.response.GenericResponse;
import ar.com.ada.api.adaairlines.services.AeropuertoService;

@RestController
public class AeropuertoController { // 21:23 el post solo crea la respuesta, el service crea el aeropuerto

    @Autowired
    AeropuertoService service;

    @PostMapping("/api/aeropuertos")
    public ResponseEntity<GenericResponse> crear(@RequestBody Aeropuerto aeropuerto) {

        GenericResponse respuesta = new GenericResponse();

        service.crear(aeropuerto.getAeropuertoId(), aeropuerto.getNombre(), aeropuerto.getCodigoIATA());

        respuesta.isOk = true;
        respuesta.message = "Se creo correctamente";
        //respuesta.id = aeropuerto.getAeropuertoId();

        return ResponseEntity.ok(respuesta);

    }
}

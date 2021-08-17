package ar.com.ada.api.adaairlines.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.adaairlines.entities.Pasaje;
import ar.com.ada.api.adaairlines.models.request.InfoPasajeNuevo;
import ar.com.ada.api.adaairlines.models.response.GenericResponse;
import ar.com.ada.api.adaairlines.services.PasajeService;

@RestController
public class PasajeController {

    @Autowired
    PasajeService service;


    @PostMapping("api/pasajes")
    public ResponseEntity<GenericResponse> emitir(@RequestBody InfoPasajeNuevo infoPasajes) {

        GenericResponse respuesta = new GenericResponse();

        Pasaje pasaje = service.emitir(infoPasajes.reservaId);

        respuesta.message = "El pasaje ha sido generada correctamente.";
        respuesta.isOk = true;
        respuesta.id = pasaje.getPasajeId();




        return ResponseEntity.ok(respuesta);
    }

    
}

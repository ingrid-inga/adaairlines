/*package ar.com.ada.api.adaairlines.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.adaairlines.entities.Pasajero;
import ar.com.ada.api.adaairlines.models.request.InfoPasajeroNuevo;
import ar.com.ada.api.adaairlines.models.response.GenericResponse;
import ar.com.ada.api.adaairlines.services.PasajeroService;


@RestController
public class PasajeroController {
    @Autowired
    private PasajeroService service;

    @GetMapping("/pasajeros")
    public ResponseEntity<List<Pasajero>> traerEmpleados() { 
        final List<Pasajero> lista = service.traerPasajeros();
        return ResponseEntity.ok(lista);
    }    

    
    @PostMapping("/pasajeros")
    public ResponseEntity<?> crearPasajero(@RequestBody InfoPasajeroNuevo pasajeroInfo) {
        GenericResponse respuesta = new GenericResponse();

        Pasajero pasajero = new Pasajero();
        pasajero.setNombre(pasajeroInfo.nombre);
       // pasajero.setFechaLogin(new Date());

    
        service.crearPasajero(pasajero);
        respuesta.isOk = true;
        respuesta.id = pasajero.getPasajeroId();
        respuesta.message = "El pasajero fue creada con exito";
        return ResponseEntity.ok(respuesta);

    }

    @GetMapping("/pasajeros/{id}")
    public ResponseEntity<Pasajero> getPasajeroPorId(@PathVariable Integer id){
        Pasajero pasajero = service.buscarPasajero(id);

        return ResponseEntity.ok(pasajero);
    }





    
}
*/
package ar.com.ada.api.adaairlines.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.adaairlines.entities.*;
import ar.com.ada.api.adaairlines.models.request.EstadoVueloRequest;
import ar.com.ada.api.adaairlines.models.response.GenericResponse;
import ar.com.ada.api.adaairlines.services.*;
import ar.com.ada.api.adaairlines.services.VueloService.ValidacionVueloDataEnum;

@RestController
public class VueloController {

    /*
     * @Autowired //Version simple VueloService service;
     */

    // Version mas "pro"
    private VueloService service;

    private AeropuertoService aeropuertoService;

    public VueloController(VueloService service, AeropuertoService aeropuertoService) {
        this.service = service;
        this.aeropuertoService = aeropuertoService;
    }


    @PostMapping("/api/vuelos")
    public ResponseEntity<GenericResponse> postCrearVuelo(@RequestBody Vuelo vuelo) {
        GenericResponse respuesta = new GenericResponse();

        ValidacionVueloDataEnum resultadoValidacion = service.validar(vuelo);
        if (resultadoValidacion == ValidacionVueloDataEnum.OK) {

            service.crear(vuelo);

            respuesta.isOk = true;
            respuesta.id = vuelo.getVueloId();
            respuesta.message = "Vuelo creado con éxito";

            return ResponseEntity.ok(respuesta);
        } else {

            respuesta.isOk = false;
            respuesta.message = "Error(" + resultadoValidacion.toString() + ")";

            return ResponseEntity.badRequest().body(respuesta);
        }
    }

    

    @PutMapping("/api/vuelos/{id}/estados")
    public ResponseEntity<GenericResponse> putActualizarEstadoVuelo(@PathVariable Integer id,
            @RequestBody EstadoVueloRequest estadoVuelo) {

        GenericResponse r = new GenericResponse();
        r.isOk = true;
        // Pasos:
        // 1 buscar vuelo por Id y lo asignamos a una variable(vuelo).
        Vuelo vuelo = service.buscarPorId(id);
        // 2 setearle el nuevo estado, que vino en estadoVuelo al vuelo.
        vuelo.setEstadoVueloId(estadoVuelo.estado);
        // 3 grabar el vuelo en la base de datos.
        service.actualizar(vuelo);

        // 4 que devuelva el status final.
        r.message = "actualizado";
        return ResponseEntity.ok(r);
    }



    @GetMapping("/api/vuelos/abiertos")
    public ResponseEntity<List<Vuelo>> getVuelosAbiertos() {

        return ResponseEntity.ok(service.traerVuelosAbiertos());
    }



    @GetMapping("/api/vuelos")
    public ResponseEntity<List<Vuelo>> traerVuelos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }



    @GetMapping("api/vuelos/{id}")
    public ResponseEntity<?> traerVueloPorId(@PathVariable Integer id) {
        GenericResponse respuesta = new GenericResponse();
        if (!service.validarVueloExiste(id)) {
            respuesta.isOk = false;
            respuesta.message = "El Id del vuelo ingresado no es válido.";
            return ResponseEntity.badRequest().body(respuesta);
        }
        return ResponseEntity.ok(service.buscarPorId(id));
    }



    @DeleteMapping("/api/vuelos/{id}")
    public ResponseEntity<GenericResponse> eliminar(@PathVariable Integer id) {

        GenericResponse respuesta = new GenericResponse();
        if (service.validarVueloExiste(id)) {
            service.eliminarVueloPorId(id);
            respuesta.isOk = true;
            respuesta.message = "El vuelo ha sido eliminado con éxito.";
            return ResponseEntity.ok(respuesta);

        } else {
            respuesta.isOk = false;
            respuesta.message = "El Id del vuelo ingresado no es válido.";
            return ResponseEntity.badRequest().body(respuesta);
        }
    }
}
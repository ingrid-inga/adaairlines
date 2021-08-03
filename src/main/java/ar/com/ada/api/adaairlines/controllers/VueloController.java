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
            respuesta.message = "Vuelo creado correctamente";

            return ResponseEntity.ok(respuesta);
        } else {

            respuesta.isOk = false;
            respuesta.message = "Error(" + resultadoValidacion.toString() + ")";

            return ResponseEntity.badRequest().body(respuesta);
        }

        /*
         * @PostMapping("/api/v2/vuelos") public ResponseEntity<GenericResponse>
         * postCrearVueloV2(@RequestBody Vuelo vuelo) { GenericResponse respuesta = new
         * GenericResponse();
         * 
         * Aeropuerto ao = aeropuertoService.b
         * 
         * Vuelo vueloCreado = service.crear(vuelo.getFecha(), vuelo.getCapacidad(),
         * vuelo.getAeropuertoOrigen(), vuelo.getAeropuertoDestino(), vuelo.getPrecio(),
         * vuelo.getCodigoMoneda());
         * 
         * respuesta.isOk = true; respuesta.id = vueloCreado.getVueloId();
         * respuesta.message = "Vuelo creado correctamente";
         * 
         * return ResponseEntity.ok(respuesta); }
         */

        /*
         * Versión simple
         * 
         * @Autowired //notacion que permite inyectar, nos evita hacer el new, es decir
         * instanciar19:27 30 july VueloService service;
         * 
         * 
         * // Version "Pro" private VueloService service;
         * 
         * public VueloController(VueloService service) { this.service = service; }
         * 
         * @PostMapping("/api/vuelos") public ResponseEntity<GenericResponse>
         * crear(@RequestBody Vuelo vuelo) { //variable vuelo de tipo vuelo
         * 
         * GenericResponse respuesta = new GenericResponse();
         * 
         * service.crear(vuelo.getVueloId());
         * 
         * respuesta.isOk = true; respuesta.message = "El vuelo se creó correctamente";
         * respuesta.id = vuelo.getVueloId();
         * 
         * return ResponseEntity.ok(respuesta);
         */
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

    // 30 july 20:12 debuggear
}
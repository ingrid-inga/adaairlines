package ar.com.ada.api.adaairlines.services;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.adaairlines.entities.Aeropuerto;
import ar.com.ada.api.adaairlines.entities.Vuelo;
import ar.com.ada.api.adaairlines.entities.Vuelo.EstadoVueloEnum;
import ar.com.ada.api.adaairlines.repos.VueloRepository;

@Service
public class VueloService { // lo más importante es esto, para la funcionalidad del negocio, aeropuerto no
                            // tiene influencia en el negocio x eso se creo primero
                            // lo ideal es primero crear service 26 July
    @Autowired
    private VueloRepository repo;

    @Autowired
    private AeropuertoService aeropService;

    public void crear(Vuelo vuelo) {
        vuelo.setEstadoVueloId(EstadoVueloEnum.GENERADO);
        repo.save(vuelo);
    }

    // mon26 19:58, diferencia entre metodos crear, tienen parametros diferentes
    public Vuelo crear(Date fecha, Integer capacidad, String aeropuertoOrigenIATA, String aeropuertoDestinoIATA,
            BigDecimal precio, String codigoMoneda) { // 20:28 july26
        Vuelo vuelo = new Vuelo();// instanciar vuelos
        vuelo.setFecha(fecha);
        vuelo.setCapacidad(capacidad);

        Aeropuerto aeropuertoOrigen = aeropService.buscarPorCodigoIATA(aeropuertoOrigenIATA);
        Aeropuerto aeropuertoDestino = aeropService.buscarPorCodigoIATA(aeropuertoDestinoIATA);

        vuelo.setAeropuertoOrigen(aeropuertoOrigen.getAeropuertoId());
        vuelo.setAeropuertoOrigen(aeropuertoDestino.getAeropuertoId());// necesitamos un metodo que nos dé el objeto
                                                                       // aeropuerto, que nos traiga el aerop. a partir
                                                                       // del codigo IATA

        vuelo.setPrecio(precio);
        vuelo.setCodigoMoneda(codigoMoneda);

        // crear(vuelo); // llama al metodo de arriba. creo un vuelo pre-fabricado
        repo.save(vuelo); // lo guarda directo en la base de datos 20:21 july 26 . creo
        // un vuelo

        return vuelo;
    }

    public ValidacionVueloDataEnum validar(Vuelo vuelo) {

        if (!validarPrecio(vuelo))
            return ValidacionVueloDataEnum.ERROR_PRECIO;

        if (!validarAeropuertoOrigenDiffDestino(vuelo))
            return ValidacionVueloDataEnum.ERROR_AEROPUERTOS_IGUALES;

        return ValidacionVueloDataEnum.OK;
    }

    public boolean validarPrecio(Vuelo vuelo) {

        if (vuelo.getPrecio() == null) {
            return false;
        }
        if (vuelo.getPrecio().doubleValue() > 0)
            return true;

        return false;
    }

    public boolean validarAeropuertoOrigenDiffDestino(Vuelo vuelo) {
        /*
         * if(vuelo.getAeropuertoDestino() != vuelo.getAeropuertoOrigen()) return true;
         * else return false;
         */
        return vuelo.getAeropuertoDestino() != vuelo.getAeropuertoOrigen();

    }

    public enum ValidacionVueloDataEnum {
        OK, ERROR_PRECIO, ERROR_AEROPUERTO_ORIGEN, ERROR_AEROPUERTO_DESTINO, ERROR_FECHA, ERROR_MONEDA,
        ERROR_CAPACIDAD_MINIMA, ERROR_CAPACIDAD_MAXIMA, ERROR_AEROPUERTOS_IGUALES, ERROR_GENERAL,
    }

    public Vuelo buscarPorId(Object vueloId) {
        return repo.findByVueloId(vueloId);
    }

    public List<Vuelo> traerVuelosAbiertos() {
        return repo.findByEstadoVueloId(EstadoVueloEnum.ABIERTO.getValue());
    }

    public void actualizar(Vuelo vuelo) {
        repo.save(vuelo);
    }

    public boolean validarVueloExiste(Integer id) {
        if (buscarPorId(id) != null) {
            return true;
        } else
            return false;
    }

}

// bigdecimal, soporta montos negativos, porque se usa para economía

// 20:35 26 july, validaciones =o

// 20:39, utilizamos boolean para validar 26 july

// primero se debe verificar si es nulo en un boolean 21:16 july26
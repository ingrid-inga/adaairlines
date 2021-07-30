package ar.com.ada.api.adaairlines.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.adaairlines.entities.Aeropuerto;
import ar.com.ada.api.adaairlines.repos.AeropuertoRepository;

@Service
public class AeropuertoService {

    @Autowired
    private AeropuertoRepository repo; // llamamos 21:12 vie 23 july

    // no es autoincremental
    public void crear(Integer aeropuertoId, String nombre, String codigoIATA) { // 21:15 vi23july

        Aeropuerto aeropuerto = new Aeropuerto(); // estamos declarando e instanciando
        aeropuerto.setAeropuertoId(aeropuertoId);
        aeropuerto.setNombre(nombre);
        aeropuerto.setCodigoIATA(codigoIATA);

        repo.save(aeropuerto);
    }

    public List<Aeropuerto> obtenerTodos() {

        return repo.findAll();

    }

    public Aeropuerto buscarPorCodigoIATA(String codigoIATA) { // se genero por recomendacion automatica . VERIFICAR
        return repo.findByCodigoIATA(codigoIATA);
    }

    public boolean validarCodigoIATA(Aeropuerto aeropuerto) {
        // SI viene algo diferente de 3, que salga.
        if (aeropuerto.getCodigoIATA().length() != 3)
            return false;

        String codigoIATA = aeropuerto.getCodigoIATA();
        // "AP"
        for (int x = 0; x < codigoIATA.length(); x++) {
            char c = codigoIATA.charAt(x);

            if (!(c >= 'A' && c <= 'Z'))
                return false;
        }
        return true;
    }

}

// findByCodigoIATA, son especiales mon26 20:09
// uso tabla ascii 21:29, july 28
// TDD: Test Driven Devolopment
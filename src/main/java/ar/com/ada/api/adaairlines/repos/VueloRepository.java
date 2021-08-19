package ar.com.ada.api.adaairlines.repos;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.adaairlines.entities.Vuelo;

@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Integer> {

    Vuelo findByVueloId(Object vueloId);

    List<Vuelo> findByEstadoVueloId(Integer value);

    List<Vuelo> findAll();

}

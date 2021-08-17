package ar.com.ada.api.adaairlines.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.adaairlines.entities.Pasaje;

@Repository
public interface PasajeRepository extends JpaRepository<Pasaje, Integer> {
     Pasaje findByPasajeId (Integer id);
}

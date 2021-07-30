package ar.com.ada.api.adaairlines.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.adaairlines.entities.Aeropuerto;

@Repository
public interface AeropuertoRepository extends JpaRepository<Aeropuerto, Integer> {

    Aeropuerto findByCodigoIATA(String codigoIATA);

}

//20:07 mon26 july

//Mier28 Los test siempre son void
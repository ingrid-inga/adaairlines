package ar.com.ada.api.adaairlines.entities;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "aeropuerto")

public class Aeropuerto {

    @Id
    @Column(name = "aeropuerto_id")
    // @GeneratedValue(strategy = GenerationType.IDENTITY). NO VA PORQUE NO ES
    // AUTOINCREMENTAR
    private Integer aeropuertoId;

    @Column(name = "nombre_aeropuerto")
    private String nombre;

    @Column(name = "codigo_iata")
    private String codigoIATA;

    // @OneToMany(mappedBy = "aeropuerto", cascade = CascadeType.ALL, fetch =
    // FetchType.LAZY)
    // private List<Vuelo> vuelos = new ArrayList<>();

    public Integer getAeropuertoId() {
        return aeropuertoId;
    }

    public void setAeropuertoId(Integer aeropuertoId) {
        this.aeropuertoId = aeropuertoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoIATA() {
        return codigoIATA;
    }

    public void setCodigoIATA(String codigoIATA) {
        this.codigoIATA = codigoIATA;
    }

    // public Vuelo getVuelo(){
    // return getVuelo();
    // }
    // public void agregarVuelo(Integer vuelo){
    // this.vuelos.add(vuelo);
    // vuelo.setAeropuertoDestino(this);
    // }

    // public void agregarVuelo(Vuelo vuelo){
    // this.vuelos.add(vuelo);
    // vuelo.setAeropuertoOrigen(this);
    // }

}

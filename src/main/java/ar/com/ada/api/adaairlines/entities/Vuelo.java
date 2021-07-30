package ar.com.ada.api.adaairlines.entities;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "vuelo")
public class Vuelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "vuelo_id")
    private Integer vueloId;

    private Date fecha;// 21:47 temporal se coloca cuando no se quiere usa hora

    @Column(name = "estado_vuelo_id")
    private Integer estadoVueloId;

    private Integer capacidad;

    // @ManyToOne
    // @JoinColumn(name = "aeropuerto_origen", referencedColumnName =
    // "aeropuerto_origen") //21:57
    @Column(name = "aeropuerto_origen")
    private Integer aeropuertoOrigen; // cuando ponemos un atributo que es un objeto no va el column. (un aeropuerto
                                      // para varios vuelos)
    // @OneToOne(mappedBy = "vuelo", cascade = CascadeType.ALL)
    // @ManyToOne
    // @JoinColumn(name = "aeropuerto_destino", referencedColumnName =
    // "aeropuerto_destino") //21:57
    @Column(name = "aeropuerto_destino")
    private Integer aeropuertoDestino; // 21:59*Integer

    private BigDecimal precio;

    @Column(name = "codigo_moneda")
    private String codigoMoneda;

    @OneToMany(mappedBy = "vuelo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas = new ArrayList<>();

    public enum EstadoVueloEnum { // crear enumerados *SOLO ADJETIVOS* 20:11
        GENERADO(1), ORIGEN_ASIGNADO(2), DESTINO_ASIGNADO(3), TRIPULACION_PREASIGNADA(4), ABIERTO(5), CONFIRMADO(6),
        REPROGRAMADO(7), CANCELADO(8), CERRADO(9);

        // NO AGREGAMOS MAS ESTADOS PORQUE ESTE SISTEMA ESTA ENFOCADO AL NEGOCIO DE
        // RESERVA NO AL TRÁFICO AEREO.(NO ES NECESARIO PARA LA VISION QUE ESTAMOS
        // ARMANDO)
        private final Integer value;

        private EstadoVueloEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static EstadoVueloEnum parse(Integer id) {
            EstadoVueloEnum status = null;
            for (EstadoVueloEnum item : EstadoVueloEnum.values()) {
                if (item.getValue().equals(id)) {
                    status = item;
                    break;
                }
            }
            return status;
        }
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getAeropuertoOrigen() {
        return aeropuertoOrigen;
    }

    public void setAeropuertoOrigen(Integer aeropuertoOrigen) {
        this.aeropuertoOrigen = aeropuertoOrigen;
    }

    public Integer getAeropuertoDestino() {
        return aeropuertoDestino;
    }

    public void setAeropuertoDestino(Integer aeropuertoDestino) {
        this.aeropuertoDestino = aeropuertoDestino;
    }

    public String getCodigoMoneda() {
        return codigoMoneda;
    }

    public void setCodigoMoneda(String codigoMoneda) {
        this.codigoMoneda = codigoMoneda;
    }

    public void agregarReserva(Reserva reserva) { // 21:36
        this.reservas.add(reserva);
        reserva.setVuelo(this);
    }

    public Integer getVueloId() {
        return vueloId;
    }

    public void setVueloId(Integer vueloId) {
        this.vueloId = vueloId;
    }

    public EstadoVueloEnum getEstadoVueloId() { // 20:13
        return EstadoVueloEnum.parse(estadoVueloId);
    }

    public void setEstadoVueloId(EstadoVueloEnum estadoVueloId) {
        this.estadoVueloId = estadoVueloId.getValue();
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

}

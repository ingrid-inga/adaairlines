package ar.com.ada.api.adaairlines.entities;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "pasajero")
public class Pasajero extends Persona {
    @Id
    @Column(name = "pasajero_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pasajeroId;

    @OneToMany(mappedBy = "pasajero", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // 21:08
    private List<Reserva> reservas = new ArrayList<>();

    @OneToOne(mappedBy = "pasajero", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // 20:27
    private Usuario usuario;

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        usuario.setPasajero(this);
    }

    public enum EstadoReservaEnum {
        INACTIVO(0), ACTIVO(1);

        private final Integer value;

        private EstadoReservaEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static EstadoReservaEnum parse(Integer id) {
            EstadoReservaEnum status = null; // Default
            for (EstadoReservaEnum item : EstadoReservaEnum.values()) {
                if (item.getValue().equals(id)) {
                    status = item;
                    break;
                }
            }
            return status;
        }
    }

    public Integer getPasajeroId() {
        return pasajeroId;
    }

    public void setPasajeroId(Integer pasajeroId) {
        this.pasajeroId = pasajeroId;
    }

    public void agregarReserva(Reserva reserva) {
        this.reservas.add(reserva);
        reserva.setPasajero(this);
    }
    // la reserva 21:17

}

package ar.com.ada.api.adaairlines.entities;

import java.util.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "pasaje")
public class Pasaje {

    @Id
    @Column(name = "pasaje_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pasajeId;

    @OneToOne // llega a pasaje es x eso que va el join column
    @JoinColumn(name = "reserva_id", referencedColumnName = "reserva_id") // 21:27
    private Reserva reserva; // 21:21 21:29 trabajamos como objetos

    @Column(name = "fecha_emision")
    private Date fechaEmision;

    @JsonIgnore
    @Column(name = "info_pago") // 21:15 aquí es Json
    private String infoPago;

    public Integer getPasajeId() {
        return pasajeId;
    }

    public void setPasajeId(Integer pasajeId) {
        this.pasajeId = pasajeId;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getInfoPago() {
        return infoPago;
    }

    public void setInfoPago(String infoPago) {
        this.infoPago = infoPago;
    }

    // public void getReserva(Reserva reserva) {
    // this.reserva.add(reserva);
    // reserva.setPasaje(this);
}

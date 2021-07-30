package ar.com.ada.api.adaairlines.entities;

import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer usuarioId;

    @NaturalId // 19:38 (mecanismo de última defensa) si se hace insertar se ejecuta por
               // segunda vez *unívoco
    private String username;

    private String password;

    private String email;

    @Column(name = "fecha_login")
    private Date fechaLogin;

    @Column(name = "tipo_usuario_id")
    private Integer tipoUsuarioId;

    @OneToOne // donde está la primary key va join column
    @JoinColumn(name = "staff_id", referencedColumnName = "staff_id")
    private Staff staff; // 19:52 lo vamos a trabajar como objeto

    @OneToOne // donde está la primary key va join column
    @JoinColumn(name = "pasajero_id", referencedColumnName = "pasajero_id")
    private Pasajero pasajero;

    public enum TipoUsuarioEnum { // explicacion del enumerado en 20:35 - 23 july
        STAFF(1), PASAJERO(2);

        private final Integer value;

        private TipoUsuarioEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static TipoUsuarioEnum parse(Integer id) {// 20:39, 23 july: sigmificado de parse
            TipoUsuarioEnum status = null;
            for (TipoUsuarioEnum item : TipoUsuarioEnum.values()) {
                if (item.getValue().equals(id)) {
                    status = item;
                    break;
                }
            }
            return status;
        }
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaLogin() {
        return fechaLogin;
    }

    public void setFechaLogin(Date fechaLogin) {
        this.fechaLogin = fechaLogin;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public TipoUsuarioEnum getTipoUsuarioId() {
        return TipoUsuarioEnum.parse(this.tipoUsuarioId);
    }

    public void setTipoUsuarioId(TipoUsuarioEnum tipoUsuarioId) {
        this.tipoUsuarioId = tipoUsuarioId.getValue();
    }

    public Integer obtenerEntityId() {
        // TODO, segun el tipo de usuario, devolver el staffId o pasajeroId o nada!

        switch (this.getTipoUsuarioId()) {
            case STAFF:
                return this.getStaff().getStaffId();
            case PASAJERO:
                return this.getPasajero().getPasajeroId();

            default:
                break;
        }
        return null;
    }

}

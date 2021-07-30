package ar.com.ada.api.adaairlines.entities;
//import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "staff")
public class Staff extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Integer staffId;

    @OneToOne(mappedBy = "staff", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // por defecto es eager
    private Usuario usuario;
    // @OneToOne(mappedBy = "staff", cascade = CascadeType.ALL)
    // la relacion birideccional va donde va el mapped 20:21

    // 20:04; donde está la fk no se crea el mapped, sino en el origen que sería el
    // primary key
    // qué pasó a las 20:10???

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        usuario.setStaff(this);
        // 20:23
    }

}

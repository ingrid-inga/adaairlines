package ar.com.ada.api.adaairlines.entities;

import java.util.*;

import javax.persistence.*;

import ar.com.ada.api.adaairlines.entities.Pais.PaisEnum;
import ar.com.ada.api.adaairlines.entities.Pais.TipoDocEnum;

@MappedSuperclass
public abstract class Persona {

    private String nombre;

    @Column(name = "tipo_documento_id")
    private Integer tipoDocumentoId;

    private String documento;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;
    // @OneToOne(mappedBy = "pasajero", cascade = CascadeType.ALL)
    @Column(name = "pais_id")
    private Integer paisId;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoDocEnum getTipoDocumentoId() {
        return TipoDocEnum.parse(this.tipoDocumentoId);
    }

    public void setTipoDocumentoId(TipoDocEnum tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId.getValue();
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public PaisEnum getPaisId() {
        return PaisEnum.parse(this.paisId);
    }

    public void setPaisId(PaisEnum paisId) {
        this.paisId = paisId.getValue();
    }

}

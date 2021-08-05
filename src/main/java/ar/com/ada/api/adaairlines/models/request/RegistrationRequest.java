package ar.com.ada.api.adaairlines.models.request;

import ar.com.ada.api.adaairlines.entities.Usuario.TipoUsuarioEnum;

public class RegistrationRequest {
    
    public String fullName; // Nombre persona
    public int country; // pais del usuario
    public int identificationType; // Tipo Documento
    public String identification; // nro documento
    //public Date birthDate; // fechaNacimiento
    public String email; // email
    public TipoUsuarioEnum userType;
    public String password; // contraseña elegida por el usuario.
}


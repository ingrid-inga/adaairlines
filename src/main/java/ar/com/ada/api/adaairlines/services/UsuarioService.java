package ar.com.ada.api.adaairlines.services;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ar.com.ada.api.adaairlines.entities.*;
import ar.com.ada.api.adaairlines.entities.Pais.PaisEnum;
import ar.com.ada.api.adaairlines.entities.Pais.TipoDocEnum;
import ar.com.ada.api.adaairlines.entities.Usuario.TipoUsuarioEnum;
import ar.com.ada.api.adaairlines.repos.UsuarioRepository;
import ar.com.ada.api.adaairlines.security.Crypto;
import ar.com.ada.api.adaairlines.sistema.comm.EmailService;

@Service
public class UsuarioService {

  @Autowired
  StaffService staffService;

  @Autowired
  UsuarioRepository usuarioRepository;

  @Autowired
  PasajeroService pasajeroService;

  @Autowired
  EmailService emailService;

  public Usuario buscarPorUsername(String username) {
    return usuarioRepository.findByUsername(username);
  }

  public Usuario login(String username, String password) {

    /**
     * Metodo IniciarSesion recibe usuario y contraseña validar usuario y contraseña
     */

    Usuario u = buscarPorUsername(username);

    if (u == null || !u.getPassword().equals(Crypto.encrypt(password, u.getEmail().toLowerCase()))) {

      throw new BadCredentialsException("Usuario o contraseña invalida");
    }

    return u;
  }

  public Usuario crearUsuario(TipoUsuarioEnum tipoUsuario, String nombre, int pais, Date fechaNacimiento,
      TipoDocEnum tipoDocumento, String documento, String email, String password) {

    Usuario usuario = new Usuario();
    usuario.setUsername(email);
    usuario.setEmail(email);
    usuario.setPassword(Crypto.encrypt(password, email.toLowerCase()));
    usuario.setTipoUsuarioId(tipoUsuario);

    if (tipoUsuario == TipoUsuarioEnum.PASAJERO) {
      Pasajero pasajero = new Pasajero();

      pasajero.setDocumento(documento);
      pasajero.setPaisId(PaisEnum.parse(pais));
      pasajero.setFechaNacimiento(fechaNacimiento);
      pasajero.setNombre(nombre);
      pasajero.setTipoDocumentoId(tipoDocumento);
      pasajero.setUsuario(usuario);

      pasajeroService.crearPasajero(pasajero);

    } else { // en este caso, asumios que si no es pasajero es staff
      Staff staff = new Staff();
      staff.setDocumento(documento);
      staff.setPaisId(PaisEnum.parse(pais));
      staff.setFechaNacimiento(fechaNacimiento);
      staff.setNombre(nombre);
      staff.setTipoDocumentoId(tipoDocumento);
      staff.setUsuario(usuario);

      staffService.crearStaff(staff);
    }

    emailService.SendEmail(usuario.getEmail(), "Registracion Exitosa", "Bienvenido, ud ha sido registrado");

    return usuario;
  }

  public Usuario buscarPorEmail(String email) {

    return usuarioRepository.findByEmail(email);
  }

  public Usuario buscarPor(Integer id) {
    Optional<Usuario> usuarioOp = usuarioRepository.findById(id);

    if (usuarioOp.isPresent()) {
      return usuarioOp.get();
    }

    return null;
  }

  public Map<String, Object> getUserClaims(Usuario usuario) {
    Map<String, Object> claims = new HashMap<>();

    claims.put("userType", usuario.getTipoUsuarioId());

    if (usuario.obtenerEntityId() != null)
      claims.put("entityId", usuario.obtenerEntityId());

    return claims;
  }

  public UserDetails getUserAsUserDetail(Usuario usuario) {
    UserDetails uDetails;

    uDetails = new User(usuario.getUsername(), usuario.getPassword(), getAuthorities(usuario));

    return uDetails;
  }

  // Usamos el tipo de datos SET solo para usar otro diferente a List private
  Set<? extends GrantedAuthority> getAuthorities(Usuario usuario) {

    Set<SimpleGrantedAuthority> authorities = new HashSet<>();

    TipoUsuarioEnum userType = usuario.getTipoUsuarioId();

    authorities.add(new SimpleGrantedAuthority("CLAIM_userType_" + userType.toString()));

    if (usuario.obtenerEntityId() != null)
      authorities.add(new SimpleGrantedAuthority("CLAIM_entityId_" + usuario.obtenerEntityId()));
    return authorities;
  }

}
package utez.edu.mx.citas.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import utez.edu.mx.citas.model.Role;
import utez.edu.mx.citas.model.Solicitante;
import utez.edu.mx.citas.model.Usuario;
import utez.edu.mx.citas.service.RolServiceImpl;
import utez.edu.mx.citas.service.SolicitanteServiceImpl;
import utez.edu.mx.citas.service.UsuarioServiceImpl;

@Controller
public class HomeController {

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @Autowired
    private RolServiceImpl roleServiceImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SolicitanteServiceImpl solicitanteServiceImpl;

    @GetMapping("/")
    public String index() {
      return "index";
    }

    @GetMapping("/index")
    public String mostrarIndex(Authentication authentication, HttpSession session) {
      String username = authentication.getName();
      System.out.println("Username: " + username);
      for(GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
        System.out.println("Role: " + grantedAuthority.getAuthority());
      }
      Usuario user = usuarioServiceImpl.buscarPorUsername(username);
      //Add data user session
      System.out.println("Nombre: " + user.getNombre());
      session.setAttribute("user", user);
      return "redirect:/";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
      return "formLogin";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
      try {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, null, null);
        redirectAttributes.addFlashAttribute("msg_success", "¡Sesión cerrada! Hasta luego");
      } catch (Exception e) {
        redirectAttributes.addFlashAttribute("msg_error", "Ocurrió un error al cerrar la sesión, intenta de nuevo.");
      }
      return "redirect:/login";
    }

    @GetMapping("/admin/dashboard")
    public String dashboardAdminstrador(Authentication authentication, HttpSession session) {
      if (session.getAttribute("user") == null) {
        Usuario user = usuarioServiceImpl.buscarPorUsername(authentication.getName());
        user.setPassword(null);
        session.setAttribute("user", user);
      }

      return "admin/dashboardAdministrador";
    }

    @GetMapping("/solicitante/dashboard")
    public String dashboardSolicitante(Authentication authentication, HttpSession session) {
      if (session.getAttribute("user") == null) {
        Usuario user = usuarioServiceImpl.buscarPorUsername(authentication.getName());
        System.out.println(user.toString());
        user.setPassword(null);
        Solicitante solicitante = solicitanteServiceImpl.buscarPorIdUsuario(user.getId());
        System.out.println(solicitante.toString());
        session.setAttribute("user", solicitante);
      }

      return "solicitante/dashboardSolicitante";
    }

    @GetMapping("/ventanilla/dashboard")
    public String dashboardVentanilla(Authentication authentication, HttpSession session) {
      if (session.getAttribute("user") == null) {
        Usuario user = usuarioServiceImpl.buscarPorUsername(authentication.getName());
        user.setPassword(null);
        session.setAttribute("user", user);
      }

      return "ventanilla/dashboardVentanilla";
    }

    // @PostMapping("/crearCuenta")
    // public String guardarUsuario(@RequestParam("tipoUsuario") String tipoUsuario, Usuario usuario,
    //     RedirectAttributes redirectAttributes) {
  
    //   // Recuperar la contraseña en texto plano
    //   String contrasenaPlano = usuario.getPassword();
  
    //   // Encriptar la contraseña
    //   String contrasenaEncriptada = passwordEncoder.encode(contrasenaPlano);
    //   System.out.println(contrasenaPlano);
    //   System.out.println(contrasenaEncriptada);
  
    //   // Asignar la contraseña encriptada
    //   usuario.setPassword(contrasenaEncriptada);
  
    //   // Aplicar tratemiento al telefono para solo guardar los numeros
    //   String telefono = usuario.getTelefono().replaceAll("[\\s]", "").replaceAll("\\(", "").replaceAll("\\)", "")
    //       .replaceAll("-", "");
    //       usuario.setTelefono(telefono);
  
    //   // Habilitar la cuenta por defecto
    //   usuario.setEnabled(true);
  
    //   // Asignar un rol de usuario de acuerdo al tipo seleccionado en el formulario
    //   Role role = null;
    //   if (tipoUsuario.equals("opcionVoluntario")) {
    //     role = roleServiceImpl.buscarPorAuthority("ROL_VOLUNTARIO");
    //   } else if (tipoUsuario.equals("opcionAdoptador")) {
    //     role = roleServiceImpl.buscarPorAuthority("ROL_ADOPTADOR");
    //   }
    //   usuario.agregarRol(role);
  
    //   boolean respuesta = usuarioServiceImpl.guardar(usuario);
    //   if (respuesta) {
    //     redirectAttributes.addFlashAttribute("msg_success", "¡Registro exitoso! Por favor inicia sesión.");
    //     return "redirect:/login";
    //   } else {
    //     redirectAttributes.addFlashAttribute("msg_error", "¡Registro fallido! Por favor intenta de nuevo.");
    //     return "redirect:/crearCuenta";
    //   }
    // }
}

package utez.edu.mx.citas.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import utez.edu.mx.citas.model.Carrera;
import utez.edu.mx.citas.model.Role;
import utez.edu.mx.citas.model.Solicitante;
import utez.edu.mx.citas.model.Usuario;
import utez.edu.mx.citas.service.CarreraServiceImpl;
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

    @Autowired
    private CarreraServiceImpl carreraServiceImpl;

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

    @GetMapping("/crearCuenta")
    public String formUsuario(Usuario usuario, Model model) {
      List<Carrera> listaCarreras= carreraServiceImpl.listar();

      model.addAttribute("listaCarreras", listaCarreras);
      return "formUsuario";
    }

    @PostMapping("/crearNuevaCuenta")
    public String guardarUsuario(@RequestParam("matricula") String matricula,
      @RequestParam("carrera") Carrera carrera, Usuario usuario, RedirectAttributes redirectAttributes) {  
      System.out.println("crear");

      //Encriptar la contraseña
      String contrasenaEncriptada = passwordEncoder.encode(usuario.getPassword());
      System.out.println(usuario.getPassword());
      //Settear datos por defecto
      usuario.setIntentos(3);
      usuario.setUsername(usuario.getCorreo());
      
      // Asignar la contraseña encriptada
      usuario.setPassword(contrasenaEncriptada);
  
      // Aplicar tratemiento al telefono para solo guardar los numeros
      String telefono = usuario.getTelefono().replaceAll("[\\s]", "").replaceAll("\\(", "").replaceAll("\\)", "")
          .replaceAll("-", "");
          usuario.setTelefono(telefono);
  
      //  Habilitar la cuenta por defecto
      usuario.setEnabled(true);
  
      // Asignar un rol de solicitante por defecto
      Role role = roleServiceImpl.buscarPorAuthority("ROL_SOLICITANTE");

      usuario.agregarRol(role);
  
      boolean respuesta = usuarioServiceImpl.guardar(usuario);
      System.out.println(respuesta);

      if (respuesta) {
        Solicitante solicitante = new Solicitante();
        solicitante.setMatricula(matricula);
        solicitante.setCarrera(carrera);
        solicitante.setUsuario(usuario);
        boolean respuesta2 = solicitanteServiceImpl.guardar(solicitante);

        if (!respuesta2) {
          redirectAttributes.addFlashAttribute("msg_error", "¡Registro fallido! Por favor intenta de nuevo.");
          return "redirect:/crearCuenta";  
        }
        
        redirectAttributes.addFlashAttribute("msg_success", "¡Registro exitoso! Por favor inicia sesión.");
        return "redirect:/login";
      } else {
        redirectAttributes.addFlashAttribute("msg_error", "¡Registro fallido! Por favor intenta de nuevo.");
        return "redirect:/crearCuenta";
      }
    }
}

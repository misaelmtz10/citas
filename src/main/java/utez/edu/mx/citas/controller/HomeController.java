package utez.edu.mx.citas.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utez.edu.mx.citas.model.Carrera;
import utez.edu.mx.citas.model.Cita;
import utez.edu.mx.citas.model.Role;
import utez.edu.mx.citas.model.Solicitante;
import utez.edu.mx.citas.model.Usuario;
import utez.edu.mx.citas.service.CarreraServiceImpl;
import utez.edu.mx.citas.service.RolServiceImpl;
import utez.edu.mx.citas.service.ServicioServiceImpl;
import utez.edu.mx.citas.service.SolicitanteServiceImpl;
import utez.edu.mx.citas.service.UsuarioServiceImpl;
import utez.edu.mx.citas.service.EmpleadoServiceImpl;
import utez.edu.mx.citas.service.VentanillaEmpleadoServiceImpl;
import utez.edu.mx.citas.service.VentanillaServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class HomeController {
    Logger logger = LoggerFactory.getLogger(HomeController.class); 

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
    
    @Autowired
    private VentanillaEmpleadoServiceImpl ventanillaEmpleadoService;
    
    @Autowired 
    private EmpleadoServiceImpl empleadoService;

    @Autowired
	  private UsuarioServiceImpl usuarioService;

    @Autowired
	  private RolServiceImpl rolService;

    @Autowired
    private VentanillaServiceImpl ventanillaServiceImpl;

    @Autowired
    private ServicioServiceImpl servicioServiceImpl;

    @GetMapping("/")
    public String index() {
      return "formLogin";
    }

    @GetMapping("/index")
    public String mostrarIndex() {
      return "redirect:/";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
      return "formLogin";
    }

    @GetMapping("/login-error")
    public String mostrarLoginError(RedirectAttributes redirectAttributes) {
      redirectAttributes.addFlashAttribute("msg_error", "Usuario y/o contraseña incorrecta");
      return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
      try {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, null, null);
        redirectAttributes.addFlashAttribute("msg_success", "¡Sesión cerrada! Hasta luego");
      } catch (Exception e) {
        logger.error(e.getMessage());
        redirectAttributes.addFlashAttribute("msg_error", "Ocurrió un error al cerrar la sesión, intenta de nuevo.");
      }
      return "redirect:/login";
    }

    @GetMapping("/admin/dashboard")
    public String dashboardAdminstrador(Usuario obj,Model model, Authentication authentication, HttpSession session) {
      try{
        if (session.getAttribute("user") == null) {
          List<Usuario> usuarios = usuarioService.listar();

          List<Role> roles = rolService.listar();
          model.addAttribute("lista", usuarios);
          model.addAttribute("listaRoles", roles);
          model.addAttribute("titulo", "Usuarios");
          Usuario user = usuarioServiceImpl.buscarPorUsername(authentication.getName());
          user.setPassword(null);
          session.setAttribute("user", user);
        }
      } catch (Exception e) {
        logger.error(e.getMessage());
      }
      return "admin/dashboardAdministrador";
    }

    @GetMapping("/solicitante/dashboard")
    public String dashboardSolicitante(Cita cita, Model model, Authentication authentication, HttpSession session) {
      try{
        if (session.getAttribute("user") == null) {
          model.addAttribute("listaVentanillas", ventanillaServiceImpl.listarActivas());
          model.addAttribute("listaServicios", servicioServiceImpl.listar());
          Usuario user = usuarioServiceImpl.buscarPorUsername(authentication.getName());
          user.setPassword(null);
          Solicitante solicitante = solicitanteServiceImpl.buscarPorIdUsuario(user.getId());
          session.setAttribute("user", solicitante);
        }
      } catch (Exception e) {
        logger.error(e.getMessage());
      }
      return "solicitante/dashboardSolicitante";
    }

    @GetMapping("/ventanilla/dashboard")
    public String dashboardVentanilla(Authentication authentication, HttpSession session) {
      try{
        if (session.getAttribute("user") == null) {
          Usuario user = usuarioServiceImpl.buscarPorUsername(authentication.getName());
          user.setPassword(null);
          long idEmpleado = empleadoService.findEmpleadoByUser(user.getId());
          session.setAttribute("ventanilla", ventanillaEmpleadoService.findVentanillaByEmpleado(idEmpleado));
          session.setAttribute("user", user);
        }
      } catch (Exception e) {
        logger.error(e.getMessage());
      }

      return "ventanilla/dashboardVentanilla";
    }

    @GetMapping("/crearCuenta")
    public String formUsuario(Usuario usuario, Model model) {
      try{
        List<Carrera> listaCarreras= carreraServiceImpl.listar();
        model.addAttribute("listaCarreras", listaCarreras);
      } catch (Exception e) {
        logger.error(e.getMessage());
      }
      return "formUsuario";
    }

    @PostMapping("/crearNuevaCuenta")
    public String guardarUsuario(@RequestParam("matricula") String matricula,
      @RequestParam("carrera") Carrera carrera, Usuario usuario, RedirectAttributes redirectAttributes) {  
      try{
        //Encriptar la contraseña
        String contrasenaEncriptada = passwordEncoder.encode(usuario.getPassword());
        //Settear datos por defecto
        usuario.setIntentos(3);
        usuario.setUsername(usuario.getCorreo());
        
        // Asignar la contraseña encriptada
        usuario.setPassword(contrasenaEncriptada);
    
        // Aplicar tratemiento al telefono para solo guardar los numeros
        String telefono = usuario.getTelefono().replaceAll("[\\s]", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", "");
        usuario.setTelefono(telefono);
    
        //  Habilitar la cuenta por defecto
        usuario.setEnabled(true);
    
        // Asignar un rol de solicitante por defecto
        Role role = roleServiceImpl.buscarPorAuthority("ROL_SOLICITANTE");
        usuario.agregarRol(role);
        boolean respuesta = usuarioServiceImpl.guardar(usuario);

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
      } catch (Exception e) {
        logger.error(e.getMessage());
        redirectAttributes.addFlashAttribute("msg_error", "¡Registro fallido! Por favor intenta de nuevo.");
        return "redirect:/crearCuenta";  
      }
    }
}

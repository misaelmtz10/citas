package utez.edu.mx.citas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import utez.edu.mx.citas.model.Role;
import utez.edu.mx.citas.model.Usuario;
import utez.edu.mx.citas.service.RolServiceImpl;
import utez.edu.mx.citas.service.SolicitanteServiceImpl;
import utez.edu.mx.citas.service.UsuarioServiceImpl;

@Controller
public class HomeController {

    @Autowired
    private UsuarioServiceImpl userServiceImplement;

    @Autowired
    private RolServiceImpl roleServiceImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SolicitanteServiceImpl solicitanteServiceImp;

    @GetMapping("/")
    public String index() {
      return "index";
    }

    @GetMapping("/index")
    public String mostrarIndex() {
      return "redirect:/";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
      return "formLogin";
    }

    @PostMapping("/crearCuenta")
    public String guardarUsuario(@RequestParam("tipoUsuario") String tipoUsuario, Usuario usuario,
        RedirectAttributes redirectAttributes) {
  
      // Recuperar la contraseña en texto plano
      String contrasenaPlano = usuario.getPassword();
  
      // Encriptar la contraseña
      String contrasenaEncriptada = passwordEncoder.encode(contrasenaPlano);
      System.out.println(contrasenaPlano);
      System.out.println(contrasenaEncriptada);
  
      // Asignar la contraseña encriptada
      usuario.setPassword(contrasenaEncriptada);
  
      // Aplicar tratemiento al telefono para solo guardar los numeros
      String telefono = usuario.getTelefono().replaceAll("[\\s]", "").replaceAll("\\(", "").replaceAll("\\)", "")
          .replaceAll("-", "");
          usuario.setTelefono(telefono);
  
      // Habilitar la cuenta por defecto
      usuario.setEnabled(true);
  
      // Asignar un rol de usuario de acuerdo al tipo seleccionado en el formulario
      Role role = null;
      if (tipoUsuario.equals("opcionVoluntario")) {
        role = roleServiceImpl.buscarPorAuthority("ROL_VOLUNTARIO");
      } else if (tipoUsuario.equals("opcionAdoptador")) {
        role = roleServiceImpl.buscarPorAuthority("ROL_ADOPTADOR");
      }
      usuario.agregarRol(role);
  
      boolean respuesta = userServiceImplement.guardar(usuario);
      if (respuesta) {
        redirectAttributes.addFlashAttribute("msg_success", "¡Registro exitoso! Por favor inicia sesión.");
        return "redirect:/login";
      } else {
        redirectAttributes.addFlashAttribute("msg_error", "¡Registro fallido! Por favor intenta de nuevo.");
        return "redirect:/crearCuenta";
      }
    }
}

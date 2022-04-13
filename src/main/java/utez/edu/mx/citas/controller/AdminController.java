package utez.edu.mx.citas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.password.PasswordEncoder;


import utez.edu.mx.citas.model.Empleado;
import utez.edu.mx.citas.model.Role;
import utez.edu.mx.citas.model.Servicio;
import utez.edu.mx.citas.model.Solicitante;
import utez.edu.mx.citas.model.Usuario;
import utez.edu.mx.citas.service.EmpleadoServiceImpl;
import utez.edu.mx.citas.service.RolServiceImpl;
import utez.edu.mx.citas.service.ServicioServiceImpl;
import utez.edu.mx.citas.service.SolicitanteServiceImpl;
import utez.edu.mx.citas.service.UsuarioServiceImpl;

import utez.edu.mx.citas.model.Empleado;
import utez.edu.mx.citas.model.Servicio;
import utez.edu.mx.citas.model.Solicitante;
import utez.edu.mx.citas.model.Usuario;
import utez.edu.mx.citas.service.EmpleadoServiceImpl;
import utez.edu.mx.citas.service.ServicioServiceImpl;
import utez.edu.mx.citas.service.SolicitanteServiceImpl;
import utez.edu.mx.citas.service.UsuarioServiceImpl;

@Controller
@RequestMapping(value="/fxAdmin")
public class AdminController {
	
	@Autowired
	private EmpleadoServiceImpl empleadoService;
	
	@Autowired
	private SolicitanteServiceImpl solicitanteService;
	
	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@Autowired
	private ServicioServiceImpl servicioService;

    @Autowired
	private RolServiceImpl rolService;

    @Autowired
	private PasswordEncoder passwordEncoder;
     
    @GetMapping("/")
    public String index(){
        return "citas/agenda";
    }

    @GetMapping("/index")
    public String mostrarIndex() {
        
		return "redirect:/";
	}
    
    @GetMapping("/usuarios/listar")

    public String listarUsuarios(Usuario user,Model model) {
        List<Usuario> usuarios = usuarioService.listar();

        List<Role> roles = rolService.listar();
        model.addAttribute("lista", usuarios);
        model.addAttribute("listaRoles", roles);
        model.addAttribute("titulo", "Usuarios");
        return "admin/usuarios/listarUsuarios";
    }

    @GetMapping("/empleados/listar")
    public String listarEmpleados(Model model) {
        List<Empleado> empleados = empleadoService.listar();
        
        model.addAttribute("list", empleados);
        model.addAttribute("titulo", "Empleados");
        return "admin/empleados/listEmpleados";
    }

    @GetMapping("/solicitantes/listar")
    public String listarSolicitantes(Model model) {
        List<Solicitante> solicitantes = solicitanteService.listar();
        
        model.addAttribute("list", solicitantes);
        model.addAttribute("titulo", "Solicitantes");
        return "admin/solicitantes/listSolicitantes";
    }
    
    //PENDIENTE: Aún no se termina de definir la relación servicio-documento 
    @GetMapping("/servicios/listar")
    public String listarServicios(Model model) {
        List<Servicio> servicios = servicioService.listar();
        
        model.addAttribute("list", servicios);
        model.addAttribute("titulo", "Servicios");
        return "admin/servicios/listServicios";
    }

    @GetMapping("/empleados/registrar")
    public String registrarEmpleado(Usuario usuario) {

        return "admin/empleados/registrarEmpleado";
    }
    

    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(Usuario usuario, Model model, RedirectAttributes redirectAttributes) {
    	usuario.setIntentos(3);
        usuario.setEnabled(true);
        usuario.setUsername(usuario.getCorreo());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        boolean guardado = usuarioService.guardar(usuario);

    	if (guardado) {
    		//mandar alert
    	} else {
    		//mandar alert
    	}
    	
        List<Usuario> usuarios = usuarioService.listar();

        List<Role> roles = rolService.listar();
        model.addAttribute("lista", usuarios);
        model.addAttribute("listaRoles", roles);
        model.addAttribute("titulo", "Usuarios");
        return "admin/usuarios/listarUsuarios";
    }

    @GetMapping("/usuarios/mostrar/{id}")
    public String mostrarUsuario() {

        return "admin/list";
    }

    @PostMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable long id, Usuario usuario, Model model, RedirectAttributes redirectAttributes) {
        System.out.println("EDITAR");
		Usuario new_usuario = usuarioService.mostrar(id);
        System.out.println(usuario.toString());
        System.out.println(new_usuario.toString());

    	usuario.setIntentos(3);
        usuario.setEnabled(true);
        usuario.setUsername(usuario.getCorreo());
        System.out.println("vacio : "+ usuario.getPassword().isEmpty());

        if(!usuario.getPassword().isEmpty()){
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }else{
            usuario.setPassword(new_usuario.getPassword());
        }
        boolean guardado = usuarioService.guardar(usuario);
       // System.out.println(usuario.toString());
    	if (guardado) {
    		//mandar alert
    	} else {
    		//mandar alert
    	}
    	return "redirect:/fxAdmin/usuarios/listar";
    }

    @GetMapping("/usuarios/eliminar/{id}")
    public String borrarUsuario() {

        return "admin/list";
    }

   

}

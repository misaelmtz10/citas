package utez.edu.mx.citas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
     
    @GetMapping("/")
    public String index(){
        return "citas/agenda";
    }

    @GetMapping("/index")
    public String mostrarIndex() {
        
		return "redirect:/";
	}
    
    @GetMapping("/usuarios/listar")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.listar();
        
        model.addAttribute("list", usuarios);
        model.addAttribute("titulo", "Usuarios");
        return "admin/usuarios/listUsuarios";
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
    
    @PostMapping("/empleados/guardar")
    public String guardarUsuario(Usuario usuario, Model model) {
    	boolean guardado = usuarioService.guardar(usuario);
    	
    	if (guardado) {
    		//mandar alert
    	} else {
    		//mandar alert
    	}
    	
    	List<Empleado> empleados = empleadoService.listar();
        
        model.addAttribute("list", empleados);
        model.addAttribute("titulo", "Empleados");
        return "admin/empleados/listEmpleados";
    }

    @GetMapping("/usuarios/mostrar/{id}")
    public String mostrarUsuario() {

        return "admin/list";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario() {

        return "admin/list";
    }

    @GetMapping("/usuarios/eliminar/{id}")
    public String borrarUsuario() {

        return "admin/list";
    }

   

}

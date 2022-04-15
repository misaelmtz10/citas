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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;

import utez.edu.mx.citas.model.Documento;
import utez.edu.mx.citas.model.ServicioDocumento;
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
import utez.edu.mx.citas.service.DocumentoServiceImpl;

@Controller
@RequestMapping(value="/admin")
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
    
    @Autowired
    private DocumentoServiceImpl documentoService;
     
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
    public String listarEmpleados(Model model, Empleado empleado) {
        List<Empleado> empleados = empleadoService.listar();
        
        List<Usuario> usuarios = usuarioService.findByEnabledFalseAndRole();
        model.addAttribute("listaUsuarios", usuarios);
        model.addAttribute("lista", empleados);
        model.addAttribute("titulo", "Empleados");
        return "admin/empleados/listaEmpleados";
    }

    @GetMapping("/solicitantes/listar")
    public String listarSolicitantes(Model model, Usuario usuario) {
        List<Solicitante> solicitantes = solicitanteService.listar();
        
        model.addAttribute("lista", solicitantes);
        model.addAttribute("titulo", "Solicitantes");
        return "admin/solicitantes/listSolicitantes";
    }
    
    @GetMapping("/servicios/listar")
    public String listarServicios(Model model, Servicio servicio, Documento documento) {
        List<Servicio> servicios = servicioService.listar();
        List<Documento> documentos = documentoService.listar();
        
        model.addAttribute("list", servicios);
        model.addAttribute("listDocumentos", documentos);
        model.addAttribute("titulo", "Servicios");
        return "admin/servicios/listServicios";
    }
    
    @PostMapping("/servicios/guardar")
    @Secured("ROLE_ADMIN")
    public String guardarServicio(Servicio servicio, Model model, RedirectAttributes redirectAttributes) {
    	
    	boolean guardado = servicioService.guardar(servicio);

        if (guardado) {
			redirectAttributes.addFlashAttribute("msg_success", "Registro Exitoso");	
		}else {
			redirectAttributes.addFlashAttribute("msg_error", "Registro Fallido");
		}
    	
    	return "redirect:/admin/servicios/listar";
    }

    @GetMapping("/empleados/registrar")
    public String registrarEmpleado(Usuario usuario) {

        return "admin/empleados/registrarEmpleado";
    }
    
    @GetMapping("/servicios/eliminar/{id}")
    @Secured("ROLE_ADMIN")
    public String eliminarServicio(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        boolean respuesta = servicioService.eliminar(id);

		if (respuesta) {
			redirectAttributes.addFlashAttribute("msg_success", "Registro Eliminado");
			
		}else {
			redirectAttributes.addFlashAttribute("msg_error", "Eliminacion Fallida");
			
		}

		return "redirect:/admin/servicios/listar";
    }
    
    @PostMapping("/servicios/editar/{id}")
    public String editarServicio(@PathVariable long id, Servicio servicio, Model model, RedirectAttributes redirectAttributes) {

        boolean guardado = servicioService.guardar(servicio);
    	
        if (guardado) {
			redirectAttributes.addFlashAttribute("msg_success", "Modificacion Exitosa");	
		}else {
			redirectAttributes.addFlashAttribute("msg_error", "Modificación Fallida");
		}
        return "redirect:/admin/servicios/listar";
    }
    

    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(Usuario usuario, Model model, RedirectAttributes redirectAttributes) {
    	usuario.setIntentos(3);
        usuario.setEnabled(false); 
        usuario.setUsername(usuario.getCorreo());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        boolean guardado = usuarioService.guardar(usuario);

        if (guardado) {
			redirectAttributes.addFlashAttribute("msg_success", "Creacion Exitosa");	
		}else {
			redirectAttributes.addFlashAttribute("msg_error", "Creacion Fallida");
		}
    	
    	return "redirect:/admin/usuarios/listar";
    }

    @GetMapping("/usuarios/mostrar/{id}")
    public String mostrarUsuario() {

        return "admin/list";
    }

    @PostMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable long id, Usuario usuario, Model model, RedirectAttributes redirectAttributes) {
		Usuario new_usuario = usuarioService.mostrar(id);

    	usuario.setIntentos(3);
        usuario.setEnabled(true);
        usuario.setUsername(usuario.getCorreo());

        if(!usuario.getPassword().isEmpty()){
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }else{
            usuario.setPassword(new_usuario.getPassword());
        }
        boolean guardado = usuarioService.guardar(usuario);
    	
        if (guardado) {
			redirectAttributes.addFlashAttribute("msg_success", "Modificacion Exitosa");	
		}else {
			redirectAttributes.addFlashAttribute("msg_error", "Modificación Fallida");
		}
    	return "redirect:/admin/usuarios/listar";
    }

    @GetMapping("/usuarios/eliminar/{id}")
    @Secured("ROLE_ADMIN")
    public String borrarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        boolean respuesta = usuarioService.eliminar(id);

		if (respuesta) {
			redirectAttributes.addFlashAttribute("msg_success", "Registro Eliminado");
			
		}else {
			redirectAttributes.addFlashAttribute("msg_error", "Eliminacion Fallida");
			
		}

    	return "redirect:/admin/usuarios/listar";
    }

    @GetMapping("/empleados/deshabilitar/{id}")
    @Secured("ROLE_ADMIN")
    public String deshabilitarEmpleado(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Empleado empleado = empleadoService.mostrarEmpleado(id);

        Usuario usuarioN = usuarioService.mostrar(empleado.getUsuario().getId());

        try{

            empleadoService.eliminar(id);
            usuarioN.setEnabled(false);
            boolean respuesta = usuarioService.guardar(usuarioN);

            if (respuesta) {
                redirectAttributes.addFlashAttribute("msg_success", "Usuario Deshabilitado");
                
            }

        }catch(Exception e){

            redirectAttributes.addFlashAttribute("msg_error", "Deshabilitacion Fallida");

        }			

    	return "redirect:/admin/empleados/listar";
    }

    @GetMapping("/solicitantes/deshabilitar/{id}")
    @Secured("ROLE_ADMIN")
    public String deshabilitarSolicitante(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Solicitante solicitante = solicitanteService.mostrarSolicitante(id);

        Usuario usuarioN = usuarioService.mostrar(solicitante.getUsuario().getId());

        try{

            solicitanteService.eliminar(id);
            usuarioN.setEnabled(false);
            boolean respuesta = usuarioService.guardar(usuarioN);
    
            if (respuesta) {
                redirectAttributes.addFlashAttribute("msg_success", "Usuario Deshabilitado");
                
            }

        }catch(Exception e){
            redirectAttributes.addFlashAttribute("msg_error", "Deshabilitacion Fallida");
        }

    	return "redirect:/admin/solicitantes/listar";
    }

    @PostMapping("/empleados/guardar")
    public String guardarEmpleado(Empleado empleado, Model model, RedirectAttributes redirectAttributes) {

        empleado.setEstatus(1);
        
        Usuario usuario = usuarioService.mostrar(empleado.getUsuario().getId()); //Actualizar estado en usuario
        usuario.setEnabled(true);
        usuarioService.guardar(usuario);
        boolean guardado = empleadoService.guardar(empleado);

        if (guardado) {
			redirectAttributes.addFlashAttribute("msg_success", "Registro Exitoso");	
		}else {
			redirectAttributes.addFlashAttribute("msg_error", "Registro Fallido");
		}
    	
    	return "redirect:/admin/empleados/listar";
    }
    
    @PostMapping("/documentos/guardar")
    @Secured("ROLE_ADMIN")
    public String guardarDocumento(Documento documento, Model model, RedirectAttributes redirectAttributes) {
        
    	boolean guardado = documentoService.guardar(documento);

        if (guardado) {
			redirectAttributes.addFlashAttribute("msg_success", "Registro Exitoso");	
		}else {
			redirectAttributes.addFlashAttribute("msg_error", "Registro Fallido");
		}
    	
    	return "redirect:/admin/servicios/listar";
    }

}

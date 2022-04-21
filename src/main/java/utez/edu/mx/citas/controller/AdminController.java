package utez.edu.mx.citas.controller;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.password.PasswordEncoder;
import utez.edu.mx.citas.model.Carrera;
import utez.edu.mx.citas.model.Documento;
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
import utez.edu.mx.citas.service.BitacoraServiceImpl;
import utez.edu.mx.citas.service.CarreraServiceImpl;
import utez.edu.mx.citas.service.DocumentoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@SessionAttributes({"user"})
@RequestMapping(value="/admin")
public class AdminController {
	
    Logger logger = LoggerFactory.getLogger(AdminController.class); 

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

    @Autowired
    private CarreraServiceImpl carreraServiceImpl;

    @Autowired
    private BitacoraServiceImpl bitacoraService;
     
    @GetMapping("/")
    public String index(){
        return "citas/agenda";
    }

    @GetMapping("/index")
    public String mostrarIndex() {
        
		return "redirect:/";
	}
    
    @GetMapping("/usuarios/listar")
    
    public String listarUsuarios(Usuario user,Model model, @ModelAttribute("user") Usuario admin) {
        try{
            List<Usuario> usuarios = usuarioService.findUsers(admin.getId());

            List<Role> roles = rolService.listar();
            model.addAttribute("lista", usuarios);
            model.addAttribute("listaRoles", roles);
            model.addAttribute("titulo", "Usuarios");
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return "admin/usuarios/listarUsuarios";
    }

    @GetMapping("/empleados/listar")
    
    public String listarEmpleados(Model model, Empleado empleado) {
        try{
            List<Empleado> empleados = empleadoService.listar();
            
            List<Usuario> usuarios = usuarioService.findByEnabledFalseAndRole();
            model.addAttribute("listaUsuarios", usuarios);
            model.addAttribute("lista", empleados);
            model.addAttribute("titulo", "Empleados");
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return "admin/empleados/listaEmpleados";
    }

    @GetMapping("/solicitantes/listar")
    
    public String listarSolicitantes(Model model, Usuario usuario) {
        try{
            List<Solicitante> solicitantes = solicitanteService.listar();
            
            model.addAttribute("lista", solicitantes);
            model.addAttribute("titulo", "Solicitantes");
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return "admin/solicitantes/listSolicitantes";
    }
    
    @GetMapping("/servicios/listar")
    
    public String listarServicios(Model model, Servicio servicio, Documento documento) {

        try{
            List<Servicio> servicios = servicioService.listar();
            List<Documento> documentos = documentoService.listar();
            List<Documento> documentosActivos = documentoService.listarActivos(1);

            
            model.addAttribute("list", servicios);
            model.addAttribute("listDocumentos", documentos);
            model.addAttribute("titulo", "Servicios");
            model.addAttribute("documentosActivos", documentosActivos);
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return "admin/servicios/listServicios";
    }
    
    @PostMapping("/servicios/guardar")
    
    public String guardarServicio(Servicio servicio, Model model, RedirectAttributes redirectAttributes, @ModelAttribute("user") Usuario user) {
    	try{
            boolean guardado = servicioService.guardar(servicio);

            if (guardado) {
                bitacoraService.registro(user.getId(), servicio.getId(), user.getCorreo() + " registró servicio: " + servicio.getNombre());
                redirectAttributes.addFlashAttribute("msg_success", "Registro Exitoso");	
            }else {
                redirectAttributes.addFlashAttribute("msg_error", "Registro Fallido");
            }
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("msg_error", "Registro Fallido");
            logger.error(e.getMessage());
        }
    	return "redirect:/admin/servicios/listar";
    }

    @GetMapping("/empleados/registrar")
    
    public String registrarEmpleado(Usuario usuario) {

        return "admin/empleados/registrarEmpleado";
    }
    
    @GetMapping("/servicios/eliminar/{id}")
    
    public String eliminarServicio(@PathVariable Long id, RedirectAttributes redirectAttributes, @ModelAttribute("user") Usuario user) {
        try{
            boolean respuesta = servicioService.eliminar(id);

            if (respuesta) {
                bitacoraService.eliminar(user.getId(), id, user.getCorreo() + " eliminó servicio " + id);
                redirectAttributes.addFlashAttribute("msg_success", "Registro Eliminado");
            }else {
                redirectAttributes.addFlashAttribute("msg_error", "Eliminación Fallida");
            }
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("msg_error", "Registro Fallido");
            logger.error(e.getMessage());
        }
		return "redirect:/admin/servicios/listar";
    }
    
    @PostMapping("/servicios/editar/{id}")
    
    public String editarServicio(@PathVariable Long id, Servicio servicio, Model model, RedirectAttributes redirectAttributes, @ModelAttribute("user") Usuario user) {
    	try{
            boolean guardado = servicioService.guardar(servicio);
            
            if (guardado) {
                bitacoraService.actualizar(user.getId(), servicio.getId(), user.getCorreo() + " actualizó servicio: " + servicio.getNombre());
                redirectAttributes.addFlashAttribute("msg_success", "Modificación Exitosa");	
            }else {
                redirectAttributes.addFlashAttribute("msg_error", "Modificación Fallida");
            }
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("msg_error", "Modificación Fallida");
            logger.error(e.getMessage());
        }
        return "redirect:/admin/servicios/listar";
    }
    

    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(Usuario usuario, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes, @ModelAttribute("user") Usuario user) {
        try{
            usuario.setIntentos(3);
            usuario.setEnabled(true); 
            usuario.setUsername(usuario.getCorreo());
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
           
            boolean guardado = usuarioService.guardar(usuario);
            logger.info(""+usuario.toString());
            
            if (guardado) {
            	Usuario last = usuarioService.findLastId(usuario.getUsername());
            	List<Integer> roles= rolService.findRolesByUser(last.getId());
            	
            	for (Integer rol: roles) { 
            		if(rol == 2) {
            			Empleado empleado = new Empleado();
            			empleado.setEstatus(1);
            			empleado.setUsuario(usuario);
            			empleadoService.guardar(empleado);
            		} 
            	}
            	bitacoraService.registro(user.getId(), usuario.getId(), user.getCorreo() + " registró a: " + usuario.getCorreo());
                redirectAttributes.addFlashAttribute("msg_success", "Registro Exitoso");	
            }else {
                redirectAttributes.addFlashAttribute("msg_error", "Registro Fallido");
            }
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("msg_error", "Registro Fallido");
            logger.error(e.getMessage());
        }
    	return "redirect:/admin/usuarios/listar";
    }

    @GetMapping("/usuarios/mostrar/{id}")
    
    public String mostrarUsuario() {

        return "admin/list";
    }

    @PostMapping("/usuarios/editar/{id}")
    
    public String editarUsuario(@PathVariable long id, Usuario usuario, Model model, RedirectAttributes redirectAttributes, @ModelAttribute("user") Usuario user) {
        try{
            Usuario new_usuario = usuarioService.mostrar(id);
            new_usuario.setRoles(usuario.getRoles());

            new_usuario.setIntentos(3);
            new_usuario.setEnabled(true);
            new_usuario.setUsername(usuario.getCorreo());
            
            Solicitante solicitante = new_usuario.getSolicitante() != null ? new_usuario.getSolicitante() : new Solicitante();
            Empleado empleado = new_usuario.getEmpleado() != null ? new_usuario.getEmpleado() : new Empleado();
            empleado.setEstatus(0);

            Set<Role> roles = new_usuario.getRoles();
            for (Role role : roles) {
                if (role.getAuthority().equals("ROL_VENTANILLA")) {
                    empleado.setEstatus(1);
                    if(empleado.getId() == null){
                        new_usuario.setEmpleado(empleado);    
                    }
                }else if(role.getAuthority().equals("ROL_SOLICITANTE")){

                    if(solicitante.getId() == null){
                        Carrera carrera = carreraServiceImpl.mostrarCarrera(1);
                        solicitante.setMatricula("por definir");
                        solicitante.setCarrera(carrera);
                        solicitante.setUsuario(new_usuario);
                    }
                    new_usuario.setSolicitante(solicitante);
                }
            }

            boolean guardado = usuarioService.guardar(new_usuario);
            
            if (guardado) {
                redirectAttributes.addFlashAttribute("msg_success", "Modificación Exitosa");	
            }else {
                redirectAttributes.addFlashAttribute("msg_error", "Modificación Fallida");
            }
        }catch(Exception e){
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg_error", "Modificación Fallida");
        }
    	return "redirect:/admin/usuarios/listar";
    }

    @GetMapping("/usuarios/eliminar/{id}")
    
    public String borrarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes, @ModelAttribute("user") Usuario user) {
        try{
            boolean respuesta = usuarioService.eliminar(id);

            if (respuesta) {
                bitacoraService.eliminar(user.getId(), id, user.getCorreo() + " eliminó a: " + id);
                redirectAttributes.addFlashAttribute("msg_success", "Registro Eliminado");
            }else {
                redirectAttributes.addFlashAttribute("msg_error", "Eliminación Fallida");
            }
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("msg_error", "Eliminación Fallida");
            logger.error(e.getMessage());
        }
    	return "redirect:/admin/usuarios/listar";
    }

    @GetMapping("/empleados/deshabilitar/{id}")
    
    public String deshabilitarEmpleado(@PathVariable Long id, RedirectAttributes redirectAttributes, @ModelAttribute("user") Usuario user) {
        Empleado empleado = empleadoService.mostrarEmpleado(id);

        Usuario usuarioN = usuarioService.mostrar(empleado.getUsuario().getId());

        try{

            empleadoService.eliminar(id);
            usuarioN.setEnabled(false);
            boolean respuesta = usuarioService.guardar(usuarioN);

            if (respuesta) {
                bitacoraService.actualizar(user.getId(), id, user.getCorreo() + " deshabilitó a: " + empleado.getUsuario().getCorreo());
                redirectAttributes.addFlashAttribute("msg_success", "Usuario Deshabilitado");
                
            }

        }catch(Exception e){
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg_error", "Deshabilitación Fallida");
        }			

    	return "redirect:/admin/empleados/listar";
    }

    @GetMapping("/solicitantes/deshabilitar/{id}")
    
    public String deshabilitarSolicitante(@PathVariable Long id, RedirectAttributes redirectAttributes, @ModelAttribute("user") Usuario user) {
        Solicitante solicitante = solicitanteService.mostrarSolicitante(id);

        Usuario usuarioN = usuarioService.mostrar(solicitante.getUsuario().getId());

        try{

            solicitanteService.eliminar(id);
            usuarioN.setEnabled(false);
            boolean respuesta = usuarioService.guardar(usuarioN);
    
            if (respuesta) {
                bitacoraService.actualizar(user.getId(), id, user.getCorreo() + " deshabilitó a: " + solicitante.getUsuario().getCorreo());
                redirectAttributes.addFlashAttribute("msg_success", "Usuario Deshabilitado");
                
            }

        }catch(Exception e){
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg_error", "Deshabilitación Fallida");
        }

    	return "redirect:/admin/solicitantes/listar";
    }

    @PostMapping("/empleados/guardar")
    
    public String guardarEmpleado(Empleado empleado, Model model, RedirectAttributes redirectAttributes, @ModelAttribute("user") Usuario user) {
        try{
            empleado.setEstatus(1);
            
            Usuario usuario = usuarioService.mostrar(empleado.getUsuario().getId()); //Actualizar estado en usuario
            usuario.setEnabled(true);
            usuarioService.guardar(usuario);
            boolean guardado = empleadoService.guardar(empleado);

            if (guardado) {
                bitacoraService.actualizar(user.getId(), empleado.getId(), user.getCorreo() + " habilitó a: " + empleado.getUsuario().getCorreo());
                redirectAttributes.addFlashAttribute("msg_success", "Habilitación Exitosa");	
            }else {
                redirectAttributes.addFlashAttribute("msg_error", "Habilitación Fallida");
            }
        }catch(Exception e){    
            redirectAttributes.addFlashAttribute("msg_error", "Habilitación Fallida");
            logger.error(e.getMessage());
        }
    	
    	return "redirect:/admin/empleados/listar";
    }
    
    @PostMapping("/documentos/guardar")
    
    public String guardarDocumento(Documento documento, Model model, RedirectAttributes redirectAttributes, @ModelAttribute("user") Usuario user) {
        try{
            boolean guardado = documentoService.guardar(documento);

            if (guardado) {
                bitacoraService.registro(user.getId(), documento.getId(), user.getCorreo() + " registró doc: " + documento.getNombre());
                redirectAttributes.addFlashAttribute("msg_success", "Registro Exitoso");	
            }else {
                redirectAttributes.addFlashAttribute("msg_error", "Registro Fallido");
            }
        }catch(Exception e){    
            redirectAttributes.addFlashAttribute("msg_error", "Registro Fallido");
            logger.error(e.getMessage());
        }
    	
    	return "redirect:/admin/servicios/listar";
    }
    
    @GetMapping("/documentos/deshabilitar/{id}")
    public String deshabilitarDocumento(@PathVariable Long id, RedirectAttributes redirectAttributes, @ModelAttribute("user") Usuario user) {
    	Documento documento = documentoService.mostrarDocumento(id);
    	documento.setEstatus(0);
    	boolean guardado = documentoService.guardar(documento);

        if (guardado) {
            bitacoraService.actualizar(user.getId(), id, user.getCorreo() + " deshabilitó: " + documento.getNombre());
            redirectAttributes.addFlashAttribute("msg_success", "Documento Deshabilitado");	
		}else {
			redirectAttributes.addFlashAttribute("msg_error", "Deshabilitación Fallida");
		}
    	
    	return "redirect:/admin/servicios/listar";
    }
    
    @PostMapping("/documentos/editar/{id}")
    public String actualizarDocumento(@PathVariable Long id, Documento documento, Model model, RedirectAttributes redirectAttributes, @ModelAttribute("user") Usuario user) {
    	boolean guardado = documentoService.guardar(documento);

        if (guardado) {
            bitacoraService.actualizar(user.getId(), id, user.getCorreo() + " editó: " + documento.getNombre());
            redirectAttributes.addFlashAttribute("msg_success", "Modificación Exitosa");	
		}else {
			redirectAttributes.addFlashAttribute("msg_error", "Modificación Fallida");
		}
    	
    	return "redirect:/admin/servicios/listar";
    }

}

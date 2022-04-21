package utez.edu.mx.citas.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utez.edu.mx.citas.model.Empleado;
import utez.edu.mx.citas.model.Usuario;
import utez.edu.mx.citas.model.Ventanilla;
import utez.edu.mx.citas.model.VentanillaEmpleado;
import utez.edu.mx.citas.service.BitacoraServiceImpl;
import utez.edu.mx.citas.service.EmpleadoServiceImpl;
import utez.edu.mx.citas.service.VentanillaEmpleadoServiceImpl;
import utez.edu.mx.citas.service.VentanillaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@SessionAttributes({"user"})
@RequestMapping(value = "/ventanillas")
public class VentanillaControler {
    
    Logger logger = LoggerFactory.getLogger(VentanillaControler.class); 

    @Autowired
    private VentanillaServiceImpl ventanillaServiceImpl;

    @Autowired
    private EmpleadoServiceImpl empleadoServiceImpl;

    @Autowired
    private VentanillaEmpleadoServiceImpl ventanillaEmpleadoService;

    @Autowired
    private BitacoraServiceImpl bitacoraService;

    @GetMapping(value="/asignar")
    
    public String asignarVentanilla(Model model, Ventanilla ventanilla, Empleado empleado,VentanillaEmpleado ventanillaEmpleado) {
        try{
            List<Ventanilla> listaVentanillas = ventanillaServiceImpl.listarActivas();
            List<Empleado> listarEmpleados = empleadoServiceImpl.listarActivos();
            List<VentanillaEmpleado> listaVentanillaEmpleados = ventanillaEmpleadoService.listarActivos();
            model.addAttribute("listaVentanillaEmpleados", listaVentanillaEmpleados); 

            model.addAttribute("listaVentanillas", listaVentanillas); 

            model.addAttribute("listarEmpleados", listarEmpleados); 
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "ventanilla/ventanilla/asignarVentanilla";
    }

    @GetMapping(value="/listar")
    
    public String listaVentanillas(Model model, Ventanilla ventanilla) {
        try{
            List<Ventanilla> listaVentanillas = ventanillaServiceImpl.listar();
            model.addAttribute("listaVentanillas", listaVentanillas); 
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "ventanilla/ventanilla/listarVentanillas";
    }

    @PostMapping("/asiganarUsuario")
    
    public String asiganarUsuario(VentanillaEmpleado ventanillaEmpleado, RedirectAttributes redirectAttributes, @ModelAttribute("user") Usuario user) {
        try{
            ventanillaEmpleado.setCreatedAt(new Date());
            ventanillaEmpleado.setEstatus(1);
            boolean guardado = ventanillaEmpleadoService.guardar(ventanillaEmpleado);
            
            if (guardado) {
                bitacoraService.actualizar(user.getId(), ventanillaEmpleado.getVentanilla().getId(), user.getCorreo() + " asignó ventanilla a: " + ventanillaEmpleado.getEmpleado().getUsuario().getCorreo());
                redirectAttributes.addFlashAttribute("msg_success", "Asignación Exitosa");	
            }else {
                redirectAttributes.addFlashAttribute("msg_error", "Asignación Fallida");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg_error", "Asignación Fallida");
        }
        return "redirect:/ventanillas/asignar";
    }

    @PostMapping("/guardar")
    
    public String guardarVentanilla(Ventanilla ventanilla, RedirectAttributes redirectAttributes, @ModelAttribute("user") Usuario user) {
        try{
            boolean guardado = ventanillaServiceImpl.guardar(ventanilla);
            
            if (guardado) {
                bitacoraService.registro(user.getId(), ventanilla.getId(), user.getCorreo() + " registró ventanilla: " + ventanilla.getNombreVentanilla());
                redirectAttributes.addFlashAttribute("msg_success", "Registro Exitoso");	
            }else {
                redirectAttributes.addFlashAttribute("msg_error", "Registro Fallido");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg_error", "Guardado Fallido");
        }
        return "redirect:/ventanillas/listar";
    }

    @PostMapping("/editar/{id}")
    
    public String reasiganrUsuario(@PathVariable long id, Ventanilla ventanilla, RedirectAttributes redirectAttributes, @ModelAttribute("user") Usuario user) {
        try{
            boolean guardado = ventanillaServiceImpl.guardar(ventanilla);
            
            if (guardado) {
                bitacoraService.actualizar(user.getId(), ventanilla.getId(), user.getCorreo() + " modificó ventanilla: " + ventanilla.getNombreVentanilla());
                redirectAttributes.addFlashAttribute("msg_success", "Modificación Exitosa");	
            }else {
                redirectAttributes.addFlashAttribute("msg_error", "Modificación Fallida");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg_error", "Modificación Fallido");
        }
    	return "redirect:/ventanillas/listar";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarVentanilla(@PathVariable Long id, RedirectAttributes redirectAttributes, @ModelAttribute("user") Usuario user) {

        Ventanilla ventanilla = ventanillaServiceImpl.mostrar(id);
    	ventanilla.setEstatus(0);
    	boolean eliminado = ventanillaServiceImpl.guardar(ventanilla);
    	
		if (eliminado) {
            bitacoraService.eliminar(user.getId(), ventanilla.getId(), user.getCorreo() + " eliminó ventanilla: " + ventanilla.getNombreVentanilla());
            redirectAttributes.addFlashAttribute("msg_success", "Ventanilla Deshabilitada");
			
		}else {
			redirectAttributes.addFlashAttribute("msg_error", "Deshabilitación Fallida");
			
		}

		return "redirect:/ventanillas/listar";
    }

    @GetMapping("/liberar/{id}")
    
    public String liberar(@PathVariable Long id, RedirectAttributes redirectAttributes, @ModelAttribute("user") Usuario user) {
        try{
            VentanillaEmpleado ventanilla = ventanillaEmpleadoService.obtenerRegistro(id);
            ventanilla.setEstatus(0);
            boolean guardado = ventanillaEmpleadoService.guardar(ventanilla);

            if (guardado) {
                bitacoraService.actualizar(user.getId(), ventanilla.getVentanilla().getId(), user.getCorreo() + " liberó ventanilla: " + ventanilla.getVentanilla().getNombreVentanilla());
                redirectAttributes.addFlashAttribute("msg_success", "Modificación Exitosa");
            }else {
                redirectAttributes.addFlashAttribute("msg_error", "Modificación Fallida");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg_error", "Modificación Fallida");
        }

        return "redirect:/ventanillas/asignar";    
    }
     
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        DateFormat dateFormat=new SimpleDateFormat("HH:mm");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor is a custom date editor
    }
}

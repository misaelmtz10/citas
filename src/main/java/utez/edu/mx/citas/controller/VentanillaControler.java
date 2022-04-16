package utez.edu.mx.citas.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import utez.edu.mx.citas.model.Empleado;
import utez.edu.mx.citas.model.Ventanilla;
import utez.edu.mx.citas.model.VentanillaEmpleado;
import utez.edu.mx.citas.service.EmpleadoServiceImpl;
import utez.edu.mx.citas.service.VentanillaEmpleadoServiceImpl;
import utez.edu.mx.citas.service.VentanillaServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping(value = "/ventanillas")
public class VentanillaControler {
    
    Logger logger = LoggerFactory.getLogger(VentanillaControler.class); 

    @Autowired
    private VentanillaServiceImpl ventanillaServiceImpl;

    @Autowired
    private EmpleadoServiceImpl empleadoServiceImpl;

    @Autowired
    private VentanillaEmpleadoServiceImpl ventanillaEmpleadoService;

     //Lista de ventanillas disponibles
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

    //Lista de ventanillas disponibles
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
    
    public String asiganarUsuario(VentanillaEmpleado ventanillaEmpleado, RedirectAttributes redirectAttributes) {
        try{
            ventanillaEmpleado.setCreatedAt(new Date());
            ventanillaEmpleado.setEstatus(1);
            boolean guardado = ventanillaEmpleadoService.guardar(ventanillaEmpleado);
            
            if (guardado) {
                redirectAttributes.addFlashAttribute("msg_success", "Guardado Exitosa");	
            }else {
                redirectAttributes.addFlashAttribute("msg_error", "Guardado Fallido");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg_error", "Guardado Fallido");
        }
        return "redirect:/ventanillas/asignar";
    }

    @PostMapping("/guardar")
    
    public String guardarVentanilla(Ventanilla ventanilla, RedirectAttributes redirectAttributes) {
        try{
            boolean guardado = ventanillaServiceImpl.guardar(ventanilla);
            
            if (guardado) {
                redirectAttributes.addFlashAttribute("msg_success", "Guardado Exitosa");	
            }else {
                redirectAttributes.addFlashAttribute("msg_error", "Guardado Fallido");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg_error", "Guardado Fallido");
        }
        return "redirect:/ventanillas/listar";
    }

    @PostMapping("/editar/{id}")
    
    public String reasiganrUsuario(@PathVariable long id, Ventanilla ventanilla, RedirectAttributes redirectAttributes) {
        try{
            boolean guardado = ventanillaServiceImpl.guardar(ventanilla);
            
            if (guardado) {
                redirectAttributes.addFlashAttribute("msg_success", "Modificacion Exitosa");	
            }else {
                redirectAttributes.addFlashAttribute("msg_error", "Modificación Fallida");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg_error", "Modificacion Fallido");
        }
    	return "redirect:/ventanillas/listar";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarVentanilla(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        Ventanilla ventanilla = ventanillaServiceImpl.mostrar(id);
    	ventanilla.setEstatus(0);
    	boolean eliminado = ventanillaServiceImpl.guardar(ventanilla);
    	
		if (eliminado) {
			redirectAttributes.addFlashAttribute("msg_success", "Registro Desactivado");
			
		}else {
			redirectAttributes.addFlashAttribute("msg_error", "Desactivación Fallida");
			
		}

		return "redirect:/ventanillas/listar";
    }

    @GetMapping("/liberar/{id}")
    
    public String liberar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try{
            //ELIMINACION LOGICA
            VentanillaEmpleado ventanilla = ventanillaEmpleadoService.obtenerRegistro(id);
            ventanilla.setEstatus(0);
            boolean guardado = ventanillaEmpleadoService.guardar(ventanilla);

            if (guardado) {
                redirectAttributes.addFlashAttribute("msg_success", "Registro Eliminado");
            }else {
                redirectAttributes.addFlashAttribute("msg_error", "Eliminación Fallida");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg_error", "Eliminación Fallida");
        }

        return "redirect:/ventanillas/asignar";    
    }
     
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //convert the date Note that the conversion here should always be in the same format as the string passed in, e.g. 2015-9-9 should be yyyy-MM-dd
        DateFormat dateFormat=new SimpleDateFormat("HH:mm");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor is a custom date editor
    }
}

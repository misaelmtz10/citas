package utez.edu.mx.citas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import utez.edu.mx.citas.model.Servicio;
import utez.edu.mx.citas.service.DocumentoServiceImpl;
import utez.edu.mx.citas.service.ServicioServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping(value="/servicios")
public class ServicioController {
    
    Logger logger = LoggerFactory.getLogger(ServicioController.class); 

    @Autowired
    private ServicioServiceImpl servicioServiceImpl;
    @Autowired
    private DocumentoServiceImpl documentoServiceImpl;
    
    //Lista de servicios disponibles
    @GetMapping(value="/lista")
    public String listaServicios(Model model, RedirectAttributes redirectAttributes) {
        try{
            List<Servicio> listaServicios = servicioServiceImpl.listar();
            model.addAttribute("listaServicios", listaServicios); 
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg_error", "Ocurrió un error al cargar");
        }
        return "citas/list";
    }

    @GetMapping(value="/crear")
    
    public String crearSevicio(Servicio servicio, Model model) {
        try{
            model.addAttribute("listaDocumentos", documentoServiceImpl.listar());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "citas/formulario";
    }
    
    @PostMapping(value="/guardar")
    
    public String guardarServicio(Servicio servicio, Model model, RedirectAttributes redirectAttributes) {
        try{
            if (servicio.getId() == null) { // Create

            } else { // Update

                Servicio servicioExistente = servicioServiceImpl.mostrarServicio(servicio.getId());
            }

            /*
            if(!multipartFile.isEmpty()) {
                // Establecer directorio local para subida de archivos; en prod: /var/www/html
                String ruta = "C:/tmp/citas/pdf-citas";
                
                String nombreImagen = ImagenUtilieria.guardarImagen(multipartFile, ruta);
                if(nombreImagen != null) {
                    servicio.setImagen(nombreImagen);
                }
                
            }
            */

            boolean respuesta = servicioServiceImpl.guardar(servicio);

            if (respuesta) {
                redirectAttributes.addFlashAttribute("msg_success","Registro exitoso");
            }else{
                redirectAttributes.addFlashAttribute("msg_error","Registro fallido");
                return "redirect:/citas/formulario";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg_error", "Registro fallido");
        }
        return "citas/list";
    }

    @GetMapping(value="/mostrar/{id}")
    public String mostrarServicio(@PathVariable long id, Model modelo, RedirectAttributes redirectAttributes) {
        try{
            Servicio servicio = servicioServiceImpl.mostrarServicio(id);
            if (servicio != null) {
                modelo.addAttribute("servicio", servicio);
                return "citas/mostrarCita";
            }

            redirectAttributes.addFlashAttribute("msg_error", "Registro no existente");
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg_error", "Registro no existente");
        }
        return "redirect:/citas/list";
    }

    @GetMapping(value="/editar/{id}")
    
    public String editarServicio(@PathVariable long id, Model model, RedirectAttributes redirectAttributes) {
        try{
            Servicio servicio = servicioServiceImpl.mostrarServicio(id);

            if (servicio != null) {
                model.addAttribute("servicio", servicio);
                return "citas/mostrarCita";
            }

            redirectAttributes.addFlashAttribute("msg_error", "Registro no encontrado.");
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg_error", "Registro no encontrado.");
        }
        return "redirect:/citas/list";
    }

    @GetMapping(value="/borrar/{id}")
    
    public String borrarServicio(@PathVariable long id, RedirectAttributes redirectAttributes){
        try{
            boolean respuesta = servicioServiceImpl.eliminar(id);
            if (respuesta) {
                redirectAttributes.addFlashAttribute("msg_success", "Eliminación exitosa");
            }else{
                redirectAttributes.addFlashAttribute("msg_success", "Eliminación fallida");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg_success", "Eliminación fallida");
        }
		return "redirect:/citas/list";
    }
}

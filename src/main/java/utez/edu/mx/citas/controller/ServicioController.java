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

import utez.edu.mx.citas.model.Documento;
import utez.edu.mx.citas.model.Servicio;
import utez.edu.mx.citas.service.DocumentoServiceImpl;
import utez.edu.mx.citas.service.ServicioServiceImpl;

@Controller
@RequestMapping(value="/servicios")
public class ServicioController {
    
    @Autowired
    private ServicioServiceImpl servicioServiceImpl;
    @Autowired
    private DocumentoServiceImpl documentoServiceImpl;
    
    //Lista de servicios disponibles
    @GetMapping(value="/lista")
    public String listaServicios(Model model, RedirectAttributes redirectAttributes) {
        List<Servicio> listaServicios = servicioServiceImpl.listar();
        model.addAttribute("listaServicios", listaServicios); 

        return "citas/list";
    }

    @GetMapping(value="/crear")
    public String crearSevicio(Servicio servicio, Model model) {
        model.addAttribute("listaDocumentos", documentoServiceImpl.listar());

        return "citas/formulario";
    }
    
    @PostMapping(value="/guardar")
    public String guardarServicio(Servicio servicio, Model model, RedirectAttributes redirectAttributes) {

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

        return "citas/list";
    }

    @GetMapping(value="/mostrar/{id}")
    public String mostrarServicio(@PathVariable long id, Model modelo, RedirectAttributes redirectAttributes) {
        Servicio servicio = servicioServiceImpl.mostrarServicio(id);
		if (servicio != null) {
			modelo.addAttribute("servicio", servicio);
			return "citas/mostrarCita";
		}

		redirectAttributes.addFlashAttribute("msg_error", "Registro no existente");
        return "redirect:/citas/list";
    }

    @GetMapping(value="/editar/{id}")
    public String editarServicio(@PathVariable long id, Model model, RedirectAttributes redirectAttributes) {
        Servicio servicio = servicioServiceImpl.mostrarServicio(id);

        if (servicio != null) {
			model.addAttribute("servicio", servicio);
			return "citas/mostrarCita";
		}

		redirectAttributes.addFlashAttribute("msg_error", "Registro no encontrado.");
        return "redirect:/citas/list";
    }

    @GetMapping(value="/borrar/{id}")
    public String borrarServicio(@PathVariable long id, RedirectAttributes redirectAttributes){
		boolean respuesta = servicioServiceImpl.eliminar(id);
		if (respuesta) {
			redirectAttributes.addFlashAttribute("msg_success", "Eliminacion exitosa");
		}else{
			redirectAttributes.addFlashAttribute("msg_success", "Eliminacion fallida");
		}
		return "redirect:/citas/list";
    }
}

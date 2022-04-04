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

import utez.edu.mx.citas.model.Cita;
import utez.edu.mx.citas.service.CitaServiceImpl;

@Controller
@RequestMapping(value="/citas")
public class CitaController {

    @Autowired
    private CitaServiceImpl citaServiceImpl;
    
    @GetMapping(value="ver-agenda")
    public String mostrarAgenda(){
        return "admin/citas/agenda";
    }
    
    //Lista de citas 
    @GetMapping(value="/lista/{userId}")
    public String listaCitas(@PathVariable(required = false) Integer userId, Model model, RedirectAttributes redirectAttributes) {
        List<Cita> listaCitas = citaServiceImpl.listar();
        model.addAttribute("listaCitas", listaCitas); 

        return "citas/list";
    }

    @GetMapping(value="/crear")
    public String crearCita(Cita cita, Model model) {
        return "citas/formulario";
    }
    
    @PostMapping(value="/guardar")
    public String guardarCita(Cita cita, Model model, RedirectAttributes redirectAttributes) {

        if (cita.getId() == null) { // Create

		} else { // Update

			// Cita citaExistente = citaServiceImpl.mostrarCita(cita.getId());
			
		}

        /*
		if(!multipartFile.isEmpty()) {
			// Establecer directorio local para subida de archivos; en prod: /var/www/html
			String ruta = "C:/tmp/citas/pdf-citas";
            
			String nombreImagen = ImagenUtilieria.guardarImagen(multipartFile, ruta);
			if(nombreImagen != null) {
				cita.setImagen(nombreImagen);
			}
            
		}
        */

		boolean respuesta = citaServiceImpl.guardar(cita);
		if (respuesta) {
			redirectAttributes.addFlashAttribute("msg_success","Registro exitoso");
		}else{
			redirectAttributes.addFlashAttribute("msg_error","Registro fallido");
			return "redirect:/citas/formulario";
		}

        return "citas/list";
    }

    @GetMapping(value="/mostrar/{id}")
    public String mostrarCita(@PathVariable long id, Model model, RedirectAttributes redirectAttributes) {
        Cita cita = citaServiceImpl.mostrarCita(id);
		if (cita != null) {
			model.addAttribute("cita", cita);
			return "citas/mostrarCita";
		}

		redirectAttributes.addFlashAttribute("msg_error", "Registro no existente");
        return "redirect:/citas/lista";
    }

    @GetMapping(value="/editar/{id}")
    public String editarCita(@PathVariable long id, Model model, RedirectAttributes redirectAttributes) {
        Cita cita = citaServiceImpl.mostrarCita(id);

        if (cita != null) {
			model.addAttribute("cita", cita);
			return "citas/mostrarCita";
		}

		redirectAttributes.addFlashAttribute("msg_error", "Registro no encontrado.");
        return "redirect:/citas/lista";
    }

    @GetMapping(value="/borrar/{id}")
    public String borrarCita(@PathVariable long id, RedirectAttributes redirectAttributes){
		boolean respuesta = citaServiceImpl.eliminar(id);
		if (respuesta) {
			redirectAttributes.addFlashAttribute("msg_success", "Eliminacion exitosa");
		}else{
			redirectAttributes.addFlashAttribute("msg_success", "Eliminacion fallida");
		}
		return "redirect:/citas/lista";
    }
}

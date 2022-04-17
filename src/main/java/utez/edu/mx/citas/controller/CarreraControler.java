
package utez.edu.mx.citas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import utez.edu.mx.citas.model.Carrera;
import utez.edu.mx.citas.service.CarreraServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping(value="/carrera")
public class CarreraControler {
    Logger logger = LoggerFactory.getLogger(CarreraControler.class); 

    @Autowired
    private CarreraServiceImpl carreraServiceImpl;
    
    @GetMapping(value="/lista")
    
    public String listaCarreras(Model model, RedirectAttributes redirectAttributes) {
        try{
            List<Carrera> listaCarreras = carreraServiceImpl.listar();
            model.addAttribute("listaCarreras", listaCarreras); 
        }catch(Exception e){    
            redirectAttributes.addFlashAttribute("msg_error", "Error desconocido");
            logger.error(e.getMessage());
        }
        return "carreras/list";
    }

    @GetMapping(value="/crear")
    
    public String crearCarrera(Carrera carrera, Model model) {
        try{
            model.addAttribute("listaCarreras", carreraServiceImpl.listar());
        }catch(Exception e){    
            logger.error(e.getMessage());
        }
        return "carreras/formulario";
    }
    
    @GetMapping(value="/mostrar/{id}")
    
    public String mostrarCarrera(@PathVariable long id, Model modelo, RedirectAttributes redirectAttributes) {
        try{
            Carrera carrera = carreraServiceImpl.mostrarCarrera(id);
            if (carrera != null) {
                modelo.addAttribute("carrera", carrera);
                return "carreras/mostrarCita";
            }
            redirectAttributes.addFlashAttribute("msg_error", "Registro no existente");
        }catch(Exception e){    
            redirectAttributes.addFlashAttribute("msg_error", "Registro no existente");
            logger.error(e.getMessage());
        }
        return "redirect:/carreras/list";
    }

    @GetMapping(value="/editar/{id}")
    
    public String editarCarrera(@PathVariable long id, Model model, RedirectAttributes redirectAttributes) {
        try{
            Carrera carrera = carreraServiceImpl.mostrarCarrera(id);

            if (carrera != null) {
                model.addAttribute("carrera", carrera);
                return "carreras/mostrarCita";
            }

            redirectAttributes.addFlashAttribute("msg_error", "Registro no encontrado.");
        }catch(Exception e){    
            redirectAttributes.addFlashAttribute("msg_error", "Registro no encontrado");
            logger.error(e.getMessage());
        }
        return "redirect:/carreras/list";
    }

    @GetMapping(value="/borrar/{id}")
    
    public String borrarCarrera(@PathVariable long id, RedirectAttributes redirectAttributes){
        try{    
            boolean respuesta = carreraServiceImpl.eliminarCarrera(id);
            if (respuesta) {
                redirectAttributes.addFlashAttribute("msg_success", "Eliminación exitosa");
            }else{
                redirectAttributes.addFlashAttribute("msg_success", "Eliminación fallida");
            }
        }catch(Exception e){    
            redirectAttributes.addFlashAttribute("msg_error", "Registro Fallido");
            logger.error(e.getMessage());
        }
        return "redirect:/carreras/list";
    }
}

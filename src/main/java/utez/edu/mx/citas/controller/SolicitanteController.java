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

import utez.edu.mx.citas.model.Carrera;
import utez.edu.mx.citas.model.Solicitante;
import utez.edu.mx.citas.service.CarreraServiceImpl;
import utez.edu.mx.citas.service.SolicitanteService;
import utez.edu.mx.citas.service.SolicitanteServiceImpl;

@Controller
@RequestMapping(value="/solicitantes")
public class SolicitanteController {

    @Autowired
    SolicitanteServiceImpl solicitanteServiceImpl;

    @Autowired
    CarreraServiceImpl carreraServiceImpl;
    
    //Lista de emplados en general
    @GetMapping("/listar")
    public String listarSolicitantes(Model model) {
        List<Solicitante> listaSolicitantes = solicitanteServiceImpl.listar();
        model.addAttribute("listaSolicitantes", listaSolicitantes); 

        return "solicitantes/lista";
    }

    @GetMapping("/formulario")
    public String formularioSolicitante(Model model) {
        List<Carrera> listaCarreras= carreraServiceImpl.listar();
        model.addAttribute("listaCarreras", listaCarreras);

        return"solicitantes/formulario";
    }
    
    @PostMapping("/guardar")
    public String guardarSolicitante(Solicitante solicitante, Model model, RedirectAttributes redirectAttributes) {
        
        if (solicitante.getId() == null) { // Create

        } else { // Update

            Solicitante solicitanteExistente = solicitanteServiceImpl.mostrarSolicitante(solicitante.getId());
        }
        
        boolean respuesta = solicitanteServiceImpl.guardar(solicitante);
        if (respuesta) {
            redirectAttributes.addFlashAttribute("msg_success","Registro exitoso");
        }else{
            redirectAttributes.addFlashAttribute("msg_error","Registro fallido");
            return "redirect:/solicitantes/formulario";
        }
        return"solicitantes/lista";
    }
 
    @GetMapping("/mostrar/{id}")
    public String mostrarSolicitante(@PathVariable long id, Model model, RedirectAttributes redirectAttributes) {

        Solicitante solicitante = solicitanteServiceImpl.mostrarSolicitante(id);
        if (solicitante != null) {
            model.addAttribute("solicitante", solicitante);
            return "solicitantes/mostrarSolicitante";
        }

        redirectAttributes.addFlashAttribute("msg_error", "Registro no existente");
        return"redirect:/solicitantes/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarSolicitante(@PathVariable long id, Model model, RedirectAttributes redirectAttributes) {
        Solicitante solicitante = solicitanteServiceImpl.mostrarSolicitante(id);

        if (solicitante != null) {
			model.addAttribute("solicitante", solicitante);
			return "solicitantes/mostrarSolicitante";
		}

		redirectAttributes.addFlashAttribute("msg_error", "Registro no encontrado.");
        return "redirect:/solicitantes/listar";

    }

    @GetMapping("/eliminar/{id}")
    public String borrarSolicitante(@PathVariable long id, RedirectAttributes redirectAttributes) {

        boolean respuesta = solicitanteServiceImpl.eliminar(id);
		if (respuesta) {
			redirectAttributes.addFlashAttribute("msg_success", "Eliminacion exitosa");
		}else{
			redirectAttributes.addFlashAttribute("msg_success", "Eliminacion fallida");
		}

        return"redirect:solicitantes/listar";
    }
}

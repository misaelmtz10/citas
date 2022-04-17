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
import utez.edu.mx.citas.service.SolicitanteServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping(value="/solicitantes")
public class SolicitanteController {

    Logger logger = LoggerFactory.getLogger(SolicitanteController.class); 

    @Autowired
    SolicitanteServiceImpl solicitanteServiceImpl;

    @Autowired
    CarreraServiceImpl carreraServiceImpl;
    
    //Lista de solicitantes en general
    @GetMapping("/listar")
    
    public String listarSolicitantes(Model model) {
        try{
            List<Solicitante> listaSolicitantes = solicitanteServiceImpl.listar();
            model.addAttribute("listaSolicitantes", listaSolicitantes); 
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return "solicitantes/lista";
    }

    @GetMapping("/formulario")
    
    public String formularioSolicitante(Model model) {
        try{
            List<Carrera> listaCarreras= carreraServiceImpl.listar();
            model.addAttribute("listaCarreras", listaCarreras);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return"solicitantes/formulario";
    }
    
    @PostMapping("/guardar")
    
    public String guardarSolicitante(Solicitante solicitante, Model model, RedirectAttributes redirectAttributes) {
        try{
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
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg_error", "Registro fallido");
        }
        return"solicitantes/lista";
    }
 
    @GetMapping("/mostrar/{id}")
    
    public String mostrarSolicitante(@PathVariable long id, Model model, RedirectAttributes redirectAttributes) {
        try{
            Solicitante solicitante = solicitanteServiceImpl.mostrarSolicitante(id);
            if (solicitante != null) {
                model.addAttribute("solicitante", solicitante);
                return "solicitantes/mostrarSolicitante";
            }
            redirectAttributes.addFlashAttribute("msg_error", "Registro no existente");
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg_error", "Registro no existente");
        }
        return"redirect:/solicitantes/listar";
    }

    @GetMapping("/editar/{id}")
    
    public String editarSolicitante(@PathVariable long id, Model model, RedirectAttributes redirectAttributes) {
        try{
            Solicitante solicitante = solicitanteServiceImpl.mostrarSolicitante(id);
            if (solicitante != null) {
                model.addAttribute("solicitante", solicitante);
                return "solicitantes/mostrarSolicitante";
            }
            redirectAttributes.addFlashAttribute("msg_error", "Registro no encontrado.");
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg_error", "Registro no encontrado.");
        }
        return "redirect:/solicitantes/listar";
    }

    @GetMapping("/eliminar/{id}")
    
    public String borrarSolicitante(@PathVariable long id, RedirectAttributes redirectAttributes) {
        try{    
            boolean respuesta = solicitanteServiceImpl.eliminar(id);
            if (respuesta) {
                redirectAttributes.addFlashAttribute("msg_success", "Eliminación exitosa");
            }else{
                redirectAttributes.addFlashAttribute("msg_success", "Eliminación fallida");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("msg_error", "Eliminación fallida");
        }
        return"redirect:solicitantes/listar";
    }
}

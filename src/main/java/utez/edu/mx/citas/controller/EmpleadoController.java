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

import utez.edu.mx.citas.model.Empleado;
import utez.edu.mx.citas.service.EmpleadoServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping(value="/empleados")
public class EmpleadoController {
    Logger logger = LoggerFactory.getLogger(EmpleadoController.class); 
    
    @Autowired
    EmpleadoServiceImpl empleadoServiceImpl;

    @GetMapping("/listar")
    
    public String listarEmpleados(Model model) {
        try{
            List<Empleado> listaEmpleados = empleadoServiceImpl.listar();
            model.addAttribute("listaEmpleados", listaEmpleados); 
        }catch(Exception e){    
            logger.error(e.getMessage());
        }
        return "admin/empleados/listaEmpleados";
    }

    @GetMapping("/formulario")
    
    public String formularioEmpleado(Model model) {
        try{
            List<Empleado> listaEmpleados= empleadoServiceImpl.listar();
            model.addAttribute("listaEmpleados", listaEmpleados);
        }catch(Exception e){    
            logger.error(e.getMessage());
        }
        return "empleados/lista";
    }
    
    @PostMapping("/guardar")
    
    public String guardarEmpleado(Empleado empleado, Model model, RedirectAttributes redirectAttributes) {
        try{
            boolean respuesta = empleadoServiceImpl.guardar(empleado);
            if (respuesta) {
                redirectAttributes.addFlashAttribute("msg_success","Registro exitoso");
            }else{
                redirectAttributes.addFlashAttribute("msg_error","Registro fallido");
                return "redirect:/empleados/formulario";
            }
        }catch(Exception e){    
            redirectAttributes.addFlashAttribute("msg_error", "Registro Fallido");
            logger.error(e.getMessage());
        }
        return "empleados/listar";
    }

    @GetMapping("/mostrar/{id}")
    
    public String mostrarEmpleado(@PathVariable long id, Model model, RedirectAttributes redirectAttributes) {
        try{
            Empleado empleado = empleadoServiceImpl.mostrarEmpleado(id);
            if (empleado != null) {
                model.addAttribute("empleado", empleado);
                return "empleados/mostrarEmpleados";
            }

            redirectAttributes.addFlashAttribute("msg_error", "Registro no existente");
        }catch(Exception e){    
            redirectAttributes.addFlashAttribute("msg_error", "Registro no existente");
            logger.error(e.getMessage());
        }
        return"redirect:/empleados/listar";
    }

    @GetMapping("/editar/{id}")
    
    public String editarEmpleado(@PathVariable long id, Model model, RedirectAttributes redirectAttributes) {
        try{
            Empleado empleado = empleadoServiceImpl.mostrarEmpleado(id);

            if (empleado != null) {
                model.addAttribute("empleado", empleado);
                return "solicitantes/mostrarSolicitante";
            }

            redirectAttributes.addFlashAttribute("msg_error", "Registro no encontrado.");
        }catch(Exception e){    
            redirectAttributes.addFlashAttribute("msg_error", "Registro Fallido");
            logger.error(e.getMessage());
        }
        return "redirect:/solicitantes/listar";
    }

    @GetMapping("/eliminar/{id}")
    
    public String borrarEmpleado(@PathVariable long id, RedirectAttributes redirectAttributes) {
        try{
            boolean respuesta = empleadoServiceImpl.eliminar(id);
            if (respuesta) {
                redirectAttributes.addFlashAttribute("msg_success", "Eliminación exitosa");
            }else{
                redirectAttributes.addFlashAttribute("msg_success", "Eliminación fallida");
            }
        }catch(Exception e){    
            redirectAttributes.addFlashAttribute("msg_error", "Eliminación Fallida");
            logger.error(e.getMessage());
        }
        return"redirect:solicitantes/listar";
    }
}

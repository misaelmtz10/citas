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

@Controller
@RequestMapping(value="/empleados")
public class EmpleadoController {
    
    @Autowired
    EmpleadoServiceImpl empleadoServiceImpl;

    //Lista de emplados en general
    @GetMapping("/listar")
    public String listarEmpleados(Model model) {
        List<Empleado> listaEmpleados = empleadoServiceImpl.listar();
        model.addAttribute("listaEmpleados", listaEmpleados); 

        return "admin/empleados/listaEmpleados";
    }

    @GetMapping("/formulario")
    public String formularioEmpleado(Model model) {
        List<Empleado> listaEmpleados= empleadoServiceImpl.listar();
        model.addAttribute("listaEmpleados", listaEmpleados);

        return "empleados/lista";
    }
    
    @PostMapping("/guardar")
    public String guardarEmpleado(Empleado empleado, Model model, RedirectAttributes redirectAttributes) {

        if (empleado.getId() == null) { // Create

        } else { // Update

            Empleado empleadoExistente = empleadoServiceImpl.mostrarEmpleado(empleado.getId());
        }
        
        boolean respuesta = empleadoServiceImpl.guardar(empleado);
        if (respuesta) {
            redirectAttributes.addFlashAttribute("msg_success","Registro exitoso");
        }else{
            redirectAttributes.addFlashAttribute("msg_error","Registro fallido");
            return "redirect:/empleados/formulario";
        }
        return "empleados/listar";
    }

    @GetMapping("/mostrar/{id}")
    public String mostrarEmpleado(@PathVariable long id, Model model, RedirectAttributes redirectAttributes) {
        Empleado empleado = empleadoServiceImpl.mostrarEmpleado(id);
        if (empleado != null) {
            model.addAttribute("empleado", empleado);
            return "empleados/mostrarEmpleados";
        }

        redirectAttributes.addFlashAttribute("msg_error", "Registro no existente");
        return"redirect:/empleados/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarEmpleado(@PathVariable long id, Model model, RedirectAttributes redirectAttributes) {

        Empleado empleado = empleadoServiceImpl.mostrarEmpleado(id);

        if (empleado != null) {
			model.addAttribute("empleado", empleado);
			return "solicitantes/mostrarSolicitante";
		}

		redirectAttributes.addFlashAttribute("msg_error", "Registro no encontrado.");
        return "redirect:/solicitantes/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String borrarEmpleado(@PathVariable long id, RedirectAttributes redirectAttributes) {

        boolean respuesta = empleadoServiceImpl.eliminar(id);
		if (respuesta) {
			redirectAttributes.addFlashAttribute("msg_success", "Eliminacion exitosa");
		}else{
			redirectAttributes.addFlashAttribute("msg_success", "Eliminacion fallida");
		}

        return"redirect:solicitantes/listar";
    }
}

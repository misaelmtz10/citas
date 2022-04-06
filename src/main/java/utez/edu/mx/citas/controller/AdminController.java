package utez.edu.mx.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/fxAdmin")
public class AdminController {
     
    @GetMapping("/")
    public String index(){
        return "citas/agenda";
    }

    @GetMapping("/index")
    public String mostrarIndex() {
        
		return "redirect:/";
	}

    //Lista de usuarios en general
    @GetMapping("/usuarios/listar")
    public String listarUsuarios() {
        
        return "admin/listas";
    }

    @GetMapping("/usuarios/formulario")
    public String formularioUsuarios() {

        return "admin/list";
    }
    
    @GetMapping("/usuarios/guardar")
    public String guardarUsuario() {

        return "admin/list";
    }

    @GetMapping("/usuarios/mostrar/{id}")
    public String mostrarUsuario() {

        return "admin/list";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario() {

        return "admin/list";
    }

    @GetMapping("/usuarios/eliminar/{id}")
    public String borrarUsuario() {

        return "admin/list";
    }

   

}

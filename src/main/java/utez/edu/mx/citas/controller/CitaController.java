package utez.edu.mx.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/citas")
public class CitaController {
    
    @GetMapping(value="ver-agenda")
    public String mostrarAgenda(){
        return "citas/agenda";
    }
}

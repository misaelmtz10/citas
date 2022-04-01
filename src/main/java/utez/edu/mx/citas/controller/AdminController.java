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
}

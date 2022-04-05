package utez.edu.mx.citas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import utez.edu.mx.citas.service.SolicitanteServiceImpl;

@Controller
public class HomeController {
    
    @Autowired
    private SolicitanteServiceImpl solicitanteServiceImp;
    
  
    @GetMapping("/")
    public String index() {
		return "index";
	}

    @GetMapping("/index")
    public String mostrarIndex() {
		return "redirect:/";
	}
}

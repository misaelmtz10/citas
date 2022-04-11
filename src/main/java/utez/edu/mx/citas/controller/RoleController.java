package utez.edu.mx.citas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import utez.edu.mx.citas.service.RolServiceImpl;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RolServiceImpl rolService;

    
    @GetMapping(value ="/find-all")
    public String findAll(@PathVariable(required = false) String tipoRol, Model model) {
        model.addAttribute("list", rolService.listar());
        return "admin/role/listRole";
    }

}

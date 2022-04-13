package utez.edu.mx.citas.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import utez.edu.mx.citas.model.Cita;
import utez.edu.mx.citas.model.Servicio;
import utez.edu.mx.citas.model.Solicitante;
import utez.edu.mx.citas.model.Usuario;
import utez.edu.mx.citas.model.Ventanilla;
import utez.edu.mx.citas.service.CitaServiceImpl;
import utez.edu.mx.citas.service.ServicioServiceImpl;
import utez.edu.mx.citas.service.SolicitanteServiceImpl;
import utez.edu.mx.citas.service.UsuarioServiceImpl;
import utez.edu.mx.citas.service.VentanillaServiceImpl;

@Controller
@RequestMapping(value = "/citas")
public class CitaController {

    @Autowired
    private CitaServiceImpl citaServiceImpl;

    @Autowired
    private ServicioServiceImpl servicioServiceImpl;

    @Autowired
    private SolicitanteServiceImpl solicitanteServiceImpl;

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @Autowired
    private VentanillaServiceImpl ventanillaServiceImpl;

    @GetMapping(value = "ver-agenda")
    public String mostrarAgenda(Cita cita, Model model) {
        List<Servicio> listaServicios = servicioServiceImpl.listar();
        model.addAttribute("listaServicios", listaServicios);
        return "ventanilla/citas/agenda";
    }

    @GetMapping(value = "agenda-solicitante")
    public String mostrarAgendaSolicitante(Cita cita, Model model) {
        List<Servicio> listaServicios = servicioServiceImpl.listar();
        model.addAttribute("listaServicios", listaServicios);
        return "solicitante/agenda-solicitante";
    }

    // Lista de citas
    @GetMapping(value = "/lista/{userId}")
    public String listaCitas(@PathVariable(required = false) String tipoMascota, Model model,
            RedirectAttributes redirectAttributes) {
        List<Cita> listaCitas = citaServiceImpl.listar();
        model.addAttribute("listaCitas", listaCitas);

        return "citas/list";
    }

    // Lista de citas asincrona
    @GetMapping(value = "/")
    public ResponseEntity<Object> citas() {
        List<Cita> listaCitas = citaServiceImpl.listar();
        return new ResponseEntity<>(listaCitas, HttpStatus.OK);
    }

    @GetMapping(value = "/crear")
    public String crearCita(Cita cita, Model model) {
        return "citas/formulario";
    }

    @PostMapping(value = "/guardar")
    public String guardarCita(Cita cita, Model model, RedirectAttributes redirectAttributes, Authentication authentication) {
        System.out.println(authentication.getName());
        try{
            Usuario user = usuarioServiceImpl.buscarPorUsername(authentication.getName());
            Solicitante solicitante = solicitanteServiceImpl.buscarPorIdUsuario(user.getId());
            Ventanilla ventanilla = ventanillaServiceImpl.obtenerVentanilla(1L);

            cita.setEstatus(1);
            cita.setRegistered(new Date());
            cita.setSolicitante(solicitante);
            cita.setVentanilla(ventanilla);

            System.out.println(cita.toString());
            boolean respuesta = citaServiceImpl.guardar(cita);
            if (respuesta) {
                redirectAttributes.addFlashAttribute("msg_success", "¡Registro exitoso!");
            } else {
                redirectAttributes.addFlashAttribute("msg_error", "¡Registro fallido!");
                return "redirect:/citas/agenda-solicitante";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/citas/agenda-solicitante";
    }

    @GetMapping(value = "/mostrar/{id}")
    public String mostrarCita(@PathVariable long id, Model modelo, RedirectAttributes redirectAttributes) {
        Cita cita = citaServiceImpl.mostrarCita(id);
        if (cita != null) {
            modelo.addAttribute("cita", cita);
            return "citas/mostrarCita";
        }

        redirectAttributes.addFlashAttribute("msg_error", "Registro no existente");
        return "redirect:/citas/list";
    }

    @GetMapping(value = "/editar/{id}")
    public String editarCita(@PathVariable long id, Model model, RedirectAttributes redirectAttributes) {
        Cita cita = citaServiceImpl.mostrarCita(id);

        if (cita != null) {
            model.addAttribute("cita", cita);
            return "citas/mostrarCita";
        }

        redirectAttributes.addFlashAttribute("msg_error", "Registro no encontrado.");
        return "redirect:/citas/list";
    }

    @GetMapping(value = "/borrar/{id}")
    public String borrarCita(@PathVariable long id, RedirectAttributes redirectAttributes) {
        boolean respuesta = citaServiceImpl.eliminar(id);
        if (respuesta) {
            redirectAttributes.addFlashAttribute("msg_success", "Eliminacion exitosa");
        } else {
            redirectAttributes.addFlashAttribute("msg_success", "Eliminacion fallida");
        }
        return "redirect:/citas/list";
    }

    @GetMapping(value = "/cambiar-estatus/{id}")
    public String cambiarEstatus(@PathVariable(value="id") long id, RedirectAttributes redirectAttributes) {
        try{
            Cita cita = citaServiceImpl.obtenerCita(id);
            cita.setEstatus(2);
            boolean respuesta = citaServiceImpl.guardar(cita);
            if (respuesta) {
                redirectAttributes.addFlashAttribute("msg_success", "Eliminacion exitosa");
            } else {
                redirectAttributes.addFlashAttribute("msg_success", "Eliminacion fallida");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return "redirect:/citas/ver-agenda";
    }
}

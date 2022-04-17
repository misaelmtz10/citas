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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import utez.edu.mx.citas.model.Cita;
import utez.edu.mx.citas.model.Solicitante;
import utez.edu.mx.citas.model.Usuario;
import utez.edu.mx.citas.service.CitaServiceImpl;
import utez.edu.mx.citas.service.ServicioServiceImpl;
import utez.edu.mx.citas.service.SolicitanteServiceImpl;
import utez.edu.mx.citas.service.UsuarioServiceImpl;
import utez.edu.mx.citas.service.VentanillaServiceImpl;
import utez.edu.mx.citas.util.ArchivoUtileria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping(value = "/citas")
public class CitaController {

    Logger logger = LoggerFactory.getLogger(CitaController.class); 

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
        return "ventanilla/agenda";
    }

    @GetMapping(value = "agenda-solicitante")
    public String mostrarAgendaSolicitante(Cita cita, Model model) {
        try{
            model.addAttribute("listaVentanillas", ventanillaServiceImpl.listarActivas());
            model.addAttribute("listaServicios", servicioServiceImpl.listar());
        }catch(Exception e){    
            logger.error(e.getMessage());
        }
        return "solicitante/agenda-solicitante";
    }

    // Lista de citas asincrona
    @GetMapping(value = "/")
    public ResponseEntity<Object> citas() {
        List<Cita> listaCitas = null;
        try{
            listaCitas = citaServiceImpl.listar();
        }catch(Exception e){    
            logger.error(e.getMessage());
        }
        return new ResponseEntity<>(listaCitas, HttpStatus.OK);
    }

    @PostMapping(value = "/guardar")
    public String guardarCita(Cita cita, Model model, RedirectAttributes redirectAttributes, Authentication authentication, 
        @RequestParam("documentoCita") MultipartFile multipartFile) {

        try{
            Usuario user = usuarioServiceImpl.buscarPorUsername(authentication.getName());
            Solicitante solicitante = solicitanteServiceImpl.buscarPorIdUsuario(user.getId());

            cita.setEstatus(1);
            cita.setRegistered(new Date());
            cita.setSolicitante(solicitante);

            if(!multipartFile.isEmpty()) {
                String ruta = "C:/citas/file-citas";
                String nombreArchivo = ArchivoUtileria.guardarArchivo(multipartFile, ruta);
                if(nombreArchivo != null) {
                    cita.setArchivo(nombreArchivo);
                }
            }

            boolean respuesta = citaServiceImpl.guardar(cita);

            if (respuesta) {
                redirectAttributes.addFlashAttribute("msg_success", "¡Registro exitoso!");
            } else {
                redirectAttributes.addFlashAttribute("msg_error", "¡Registro fallido!");
                return "redirect:/citas/agenda-solicitante";
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return "redirect:/citas/agenda-solicitante";
    }

    @GetMapping(value = "/cambiar-estatus/{id}")
    public String cambiarEstatus(@PathVariable(value="id") long id, RedirectAttributes redirectAttributes) {
        try{
            Cita cita = citaServiceImpl.obtenerCita(id);
            cita.setEstatus(2);
            boolean respuesta = citaServiceImpl.guardar(cita);
            if (respuesta) {
                redirectAttributes.addFlashAttribute("msg_success", "Eliminación exitosa");
            } else {
                redirectAttributes.addFlashAttribute("msg_success", "Eliminación fallida");
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        
        return "redirect:/citas/ver-agenda";
    }
}

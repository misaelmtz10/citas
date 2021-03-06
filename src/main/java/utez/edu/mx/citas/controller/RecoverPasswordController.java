package utez.edu.mx.citas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import utez.edu.mx.citas.model.Usuario;
import utez.edu.mx.citas.service.EmailServiceImpl;
import utez.edu.mx.citas.service.UsuarioServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class RecoverPasswordController {
    Logger logger = LoggerFactory.getLogger(RecoverPasswordController.class); 

	@Autowired
	EmailServiceImpl emailServiceImpl;

	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;

	@Autowired
	private PasswordEncoder passwordEncoder;

	String NUMEROS = "0123456789";
	String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";

	public String generarContrasena(int length) {
		return contrasenaAleatoria(NUMEROS + MAYUSCULAS + MINUSCULAS, length);
	}

	public String contrasenaAleatoria(String key, int length) {
		String contrasena = "";
		for (int i = 0; i < length; i++) {
			contrasena += (key.charAt((int) (Math.random() * key.length())));
		}
		return contrasena;
	}

	@GetMapping("/reset/password")
	public String recuperarContrasena() {
		return "forgotPassword";
	}

	@PostMapping("/reset/password/email")
	public String enviarContrasenaEmail(@RequestParam("username") String username,
			RedirectAttributes redirectAttributes) {
		try{
			// Remove white spaces
			username = username.replaceAll("[\\s]", "");
			// Create new password with 12 characters
			String nuevaContrasena = generarContrasena(12);
			// Encoder password
			String contrasenaEncriptada = passwordEncoder.encode(nuevaContrasena);
			// Search user_name
			Usuario user = usuarioServiceImpl.buscarPorUsername(username);
			// Update password
			boolean respuestaCambio = usuarioServiceImpl.cambiarContrasena(contrasenaEncriptada, user.getUsername());
			// Get full user_name
			String nombreUsuario = user.getNombre().concat(" ").concat(user.getApellidos());
			// Create email content
			String htmlContent = plantillaRecuperacionContrasena(nombreUsuario, user.getUsername(), nuevaContrasena);
			// Send message
			boolean respuestaEmail = emailServiceImpl.sendEmail(user.getCorreo(), "Cambio de contrase??a", htmlContent);

			if (respuestaCambio && respuestaEmail) {
				redirectAttributes.addFlashAttribute("msg_success",
						"Correo de recuperaci??n de contrase??a enviado, por favor revisa tu bandeja de correo.");
				return "redirect:/login";
			} else {
				redirectAttributes.addFlashAttribute("msg_error", "Ocurri?? un error, por favor intenta de nuevo.");
				return "redirect:/reset/password";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute("msg_error", "Ocurri?? un error, por favor intenta de nuevo.");
			return "redirect:/reset/password";
		}
	}

	public String plantillaRecuperacionContrasena(String nombreUsuario, String email, String contrasena) {
		StringBuilder contenidoCorreo = new StringBuilder();
		contenidoCorreo.append("<!doctype html>");
		contenidoCorreo.append("<html lang=\"es\"");
		contenidoCorreo.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
		contenidoCorreo.append("<meta charset=\"UTF-8\">");
		contenidoCorreo.append("<head>");
		contenidoCorreo.append("<style>");
		contenidoCorreo.append(".h1, .h2, .h3 { font-family: Arial, Helvetica, sans-serif; }");
		contenidoCorreo.append("</style>");
		contenidoCorreo.append("</head>");
		contenidoCorreo.append("<body>");
		contenidoCorreo.append("<center><h1 style=\"color: #398AB9\">Cambio de contrase??a</h1></center>");
		contenidoCorreo.append("<center><h2 style=\"color: #006778\">Estimad@ ").append(nombreUsuario)
				.append("</h2></center>");
		contenidoCorreo.append(
				"<center><h2 style=\"color: #333\">Hemos recibido una solicitud de cambio de contrase??a</h2></center>");
		contenidoCorreo.append("<center><h2 style=\"color: #0093AB\">Nuevos datos de acceso a tu cuenta</h2></center>");
		contenidoCorreo.append("<center><h2 style=\"color: #00AFC1\">Correo electr??nico: ").append(email)
				.append("</h2></center>");
		contenidoCorreo.append("<center><h2 style=\"color: #00AFC1\">Contrase??a: ").append(contrasena)
				.append("</h2></center>");
		contenidoCorreo.append(
				"<br><center><h3 style=\"color: #333\">Por favor ingresa a tu cuenta con los datos proporcionados en este correo.<br>Recuerda cambiar la contrase??a por una de tu preferencia.</h3></center>");
		contenidoCorreo.append("</body>");
		contenidoCorreo.append("</html>");
		return contenidoCorreo.toString();
	}

}

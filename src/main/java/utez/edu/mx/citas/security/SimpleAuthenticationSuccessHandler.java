package utez.edu.mx.citas.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		boolean hasAdministradorRole = false;
		boolean hasVentanillaRole = false;
		boolean hasSolicitanteRole = false;

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROL_ADMINISTRADOR")) {
				hasAdministradorRole = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("ROL_VENTANILLA")) {
				hasVentanillaRole = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("ROL_SOLICITANTE")) {
				hasSolicitanteRole = true;
				break;
			}
		}
		
		if (hasAdministradorRole) {
			redirectStrategy.sendRedirect(request, response, "/admin/dashboard");
		} else if (hasVentanillaRole) {
			redirectStrategy.sendRedirect(request, response, "/ventanilla/dashboard");
		} else if (hasSolicitanteRole) {
			redirectStrategy.sendRedirect(request, response, "/solicitante/dashboard");
		} else {
			redirectStrategy.sendRedirect(request, response, "/login");
		}
	}

}
package utez.edu.mx.citas.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{
    
    @Autowired
	private DataSource dataSource;

    @Autowired
    private SimpleAuthenticationSuccessHandler successHandler;


	 @Override
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	 	auth.jdbcAuthentication().dataSource(dataSource)
	 			.usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?")
	 			.authoritiesByUsernameQuery("SELECT u.username, r.authority FROM user_role AS ur "
	 					+ "INNER JOIN users AS u ON u.id = ur.user_id "
	 					+ "INNER JOIN roles AS r ON r.id = ur.role_id WHERE u.username = ?");
	 }

     @Override
     public void configure(HttpSecurity httpSecurity) throws Exception {
         httpSecurity.authorizeRequests().antMatchers(
        		 "/css/**", "/js/**", "/images/**", "/file-citas/**").permitAll()
        
                 .antMatchers("/", "/crearCuenta","/crearNuevaCuenta","/reset/password/**").permitAll()
                 .antMatchers("/admin/**").hasAnyAuthority("ROL_ADMINISTRADOR")
                 .antMatchers("/citas/ver-agenda", "/cambiar-estatus/*").hasAnyAuthority("ROL_VENTANILLA")
                 .antMatchers("/citas/agenda-solicitante", "/citas/guardar").hasAnyAuthority( "ROL_SOLICITANTE")
                 .antMatchers("/citas/").hasAnyAuthority( "ROL_SOLICITANTE", "ROL_VENTANILLA")
                 .antMatchers("/carrera/**").hasAnyAuthority("ROL_ADMINISTRADOR")
                 .antMatchers("/carrera/lista", "/carrera/mostrar/**").hasAnyAuthority("ROL_SOLICITANTE")
                 .antMatchers("/empleados/**").hasAnyAuthority("ROL_ADMINISTRADOR")
                 .antMatchers("/servicios/getServicio/*").hasAnyAuthority("ROL_SOLICITANTE")
                 .antMatchers("/servicios/**").hasAnyAuthority("ROL_ADMINISTRADOR")
                 .antMatchers("/solicitantes/**").hasAnyAuthority("ROL_ADMINISTRADOR")
                 .antMatchers("/ventanillas/asignar", "/ventanillas/listar", "/ventanillas/asiganarUsuario", "/ventanillas/guardar", "/ventanillas/editar/**", "/ventanillas/eliminar/**", "/ventanillas/liberar/**").hasAnyAuthority("ROL_ADMINISTRADOR")
                 .antMatchers("/ventanillas/listar").hasAnyAuthority("ROL_ADMINISTRADOR")

                 .anyRequest().authenticated()

                .and().formLogin().successHandler(successHandler).loginPage("/login").permitAll()
                .and().formLogin().loginPage("/login").failureUrl("/login-error");
     }

    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

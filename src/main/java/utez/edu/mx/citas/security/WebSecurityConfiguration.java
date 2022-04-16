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
                 // Los recursos estaticos no requieren autenticacion
                 "/css/**", "/js/**", "/images/**", "/citas/**").permitAll()
                 // Las URL publicas no requieren autenticacion
                 .antMatchers("/", "/crearCuenta","/crearNuevaCuenta","/reset/password/**").permitAll()

                //  Asignar permisos a las URL de acuerdo a los roles
                 .antMatchers("/admin/**").hasAnyAuthority("ROL_ADMINISTRADOR")
                 .antMatchers("/citas/**").hasAnyAuthority("ROL_SOLICITANTE", "ROL_VENTANILLA")

                 // Las demas URL requieren autenticacion
                 .anyRequest().authenticated()

                //  Formulario de inicio de sesion no requiere autenticacion
                .and().formLogin().successHandler(successHandler).loginPage("/login").permitAll();
     }

    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

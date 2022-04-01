package utez.edu.mx.citas.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{
    
    @Autowired
	private DataSource dataSource;

	// @Override
	// protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	// 	auth.jdbcAuthentication().dataSource(dataSource)
	// 			.usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?")
	// 			.authoritiesByUsernameQuery("SELECT u.username, r.authority FROM user_role AS ur "
	// 					+ "INNER JOIN users AS u ON u.id = ur.user_id "
	// 					+ "INNER JOIN roles AS r ON r.id = ur.role_id WHERE u.username = ?");
	// }

    // @Override
    // public void configure(HttpSecurity httpSecurity) throws Exception {
    //     httpSecurity.authorizeRequests().antMatchers(
    //             // Los recursos estaticos no requieren autenticacion
    //             "/css/**", "/js/**", "/image/**", "/imagenes/**").permitAll()
    //             // Las URL publicas no requieren autenticacion
    //             .antMatchers("/", "/signup").permitAll()

    //             // Asignar permisos a las URL de acuerdo a los roles
    //             .antMatchers("/mascotas/**").hasAnyAuthority("ROL_VOLUNTARIO", "ROL_ADMINISTRADOR")
    //             .antMatchers("/usuarios/**").hasAnyAuthority("ROL_ADMINISTRADOR")

    //             // Las demas URL requieren autenticacion
    //             .anyRequest().authenticated()

    //             // Formulario de inicio de sesion no requiere autenticacion
    //             .and().formLogin().permitAll();
    // }
}

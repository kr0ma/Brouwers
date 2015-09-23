package be.vdab.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CreateSecurityFilter extends WebSecurityConfigurerAdapter {
	private static final String ADMINISTRATOR = "administrator";
	private static final String USER = "user";

	private static final String USERS_BY_USERNAME = "select naam as username, paswoord as password, actief as enabled"
			+ " from gebruikers where naam = ?";
	private static final String AUTHORITIES_BY_USERNAME = "select gebruikers.naam as username, rollen.naam as authorities"
			+ " from gebruikers inner join gebruikersrollen" + " on gebruikers.id = gebruikersrollen.gebruikerid"
			+ " inner join rollen" + " on rollen.id = gebruikersrollen.rolid" + " where gebruikers.naam= ?";

	@Autowired
	private DataSource dataSource; 

	@Override	
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// auth.inMemoryAuthentication().withUser("joe").password("theboss").authorities(MANAGER).and().withUser("averell").password("hungry").authorities(HELPDESKMEDEWERKER,
		// MAGAZIJNIER);
		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(USERS_BY_USERNAME)
				.authoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME)
				.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**").antMatchers("/styles/**").antMatchers("/scripts/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login").and().logout().logoutSuccessUrl("/").and().authorizeRequests()
				.antMatchers("/brouwers/toevoegen").hasAuthority(ADMINISTRATOR)
				.antMatchers("/brouwers", "/brouwers/beginnaam", "/brouwers/alfabet").hasAnyAuthority(ADMINISTRATOR, USER)
				.antMatchers("/", "/login").permitAll()
				.antMatchers("/**").authenticated().and()
				.exceptionHandling().accessDeniedPage("/WEB-INF/JSP/forbidden.jsp");
		http.httpBasic(); // rest auth
	}
}

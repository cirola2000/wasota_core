package wasota.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import wasota.exceptions.CannotAddMexNamespaces;
import wasota.graph.WasotaMainGraph;
import wasota.properties.WasotaProperties;
import wasota.services.authentication.UserAuth;
import wasota.services.currentservices.CurrentAuthenticationService;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class Application {

	public Application() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		new WasotaProperties().loadProperties();

		SpringApplication.run(Application.class, args);
		try {
			WasotaMainGraph.mainGraph.addMexNamespacesToModel();
		} catch (CannotAddMexNamespaces e) {
			e.printStackTrace();
		}

		WasotaMainGraph.mainGraph.loadModelsFromDisk();

	}

}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService());
	}

	@Bean
	UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

				UserAuth user = CurrentAuthenticationService.authService.loadUser(username);

				if (user != null)
					return new User(user.getUser(), user.getPass(), true, true, true, true,
							AuthorityUtils.createAuthorityList("USER"));
				else
					throw new UsernameNotFoundException("Could not find the user '" + username + "'");
			}

		};
	}
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(
				"/graph", "/user/add", "/context", "/performance", "/performance/get"
				).permitAll().anyRequest().authenticated()
		.and().httpBasic().and().csrf().disable();
	}

}

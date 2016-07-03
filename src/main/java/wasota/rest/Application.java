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

import wasota.core.WasotaAPI;
import wasota.core.authentication.UserAuth;
import wasota.core.authentication.impl.UserAuthenticationMongoImpl;
import wasota.core.experiments.impl.ExperimentServicesImpl;
import wasota.core.graph.impl.GraphServiceImpl;
import wasota.core.graph.impl.GraphStoreFSImpl;
import wasota.core.graph.impl.GraphUserServiceImpl;
import wasota.core.graph.impl.WasotaGraphJenaImpl;
import wasota.properties.WasotaProperties;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		new WasotaProperties().loadProperties();		
		
		SpringApplication.run(Application.class, args);
		
		// set API implementations
		WasotaAPI.setAuthServiceImplementation(new UserAuthenticationMongoImpl());
		WasotaAPI.setExperimentImplementation(new ExperimentServicesImpl());
		WasotaAPI.setGraphServiceImplementation(new GraphServiceImpl());
		WasotaAPI.setGraphStoreImplementation(new GraphStoreFSImpl());
		WasotaAPI.setWasotaGraphImplementation(new WasotaGraphJenaImpl()); 
		WasotaAPI.setGraphUserService(new GraphUserServiceImpl());

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

				UserAuth user = WasotaAPI.getAuthService().loadUser(username);

				if (user != null)
					return new User(user.getUser(), user.getPassword(), true, true, true, true,
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

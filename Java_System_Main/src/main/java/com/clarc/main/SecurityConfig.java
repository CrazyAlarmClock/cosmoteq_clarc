package com.clarc.main;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import javax.sql.DataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Configuration
	@Order(1)
	public static class App1ConfigurationAdapter extends WebSecurityConfigurerAdapter {
	    
		@Autowired
		private DataSource dataSource;

		public App1ConfigurationAdapter() {
	        super();
	    }

		//@Override
		protected void configure(AuthenticationManagerBuilder auth)
		    throws Exception {
			
			auth.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery(
				    "select id, adminPassword, adminStatus from clarc_admins " +
					"where adminLogin=?")
				.authoritiesByUsernameQuery(
		            "select id, adminAuthority from clarc_admins " +
		            "where id=?")
				.passwordEncoder(new BCryptPasswordEncoder());
		}
		
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.antMatcher("/admin*")
	          .authorizeRequests()
	          .anyRequest()
	          .hasRole("ADMIN")
	           
	          .and()
	          .formLogin()
	          .loginPage("/mainadmin")
	          .loginProcessingUrl("/adminMain")
	          .failureUrl("/mainadmin?error=loginError")
	          .defaultSuccessUrl("/adminMain")
              .usernameParameter("lgnxa")
              .passwordParameter("pwdxa")
     
	          .and()
	          .logout()
	          .logoutUrl("/logout")
	          .logoutSuccessUrl("/mainadmin")
	          .deleteCookies("JSESSIONID")
	          //.permitAll()
	           
	          .and()
	          .exceptionHandling()
	          .accessDeniedPage("/403");
	    }
	}

	@Configuration
	@Order(2)
	public static class App2ConfigurationAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
		private DataSource dataSource;

	    public App2ConfigurationAdapter() {
	        super();
	    }

		@Override
		protected void configure(AuthenticationManagerBuilder auth)
		    throws Exception {
			
			auth.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery(
				    "select id, userPassword, status from clarc_users " +
					"where userLogin=?")
				.authoritiesByUsernameQuery(
		            "select id, userAuthority from clarc_users " +
		            "where id=?")
				.passwordEncoder(new BCryptPasswordEncoder());
		}

	    protected void configure(HttpSecurity http) throws Exception {
	    	http.sessionManagement()
	           .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
	    	
	        http.authorizeRequests()
	          .antMatchers("/managerMain", "/managerUsers", "/managerAdmins")
	          .hasRole("ADMIN")
	           
	          .and()
	          .formLogin()
	          .loginPage("/managermain")
	          .failureUrl("/managermain?error=loginError")
	          .defaultSuccessUrl("/redirectToAccount", true)
              .usernameParameter("lgnx")
              .passwordParameter("pwdx")
	           
	          .and()
	          .logout()
	          .logoutUrl("/logout")
	          .logoutSuccessUrl("/")
	          .deleteCookies("JSESSIONID")
	          //.permitAll()
	           
	          .and()
	          .exceptionHandling()
	          .accessDeniedPage("/403");
	    }
	}
}

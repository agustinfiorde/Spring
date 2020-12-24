package com.perrosv13.app.configuraciones;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableScheduling
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class ConfiguracionesSeguridad extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/css/*", "/js/*", "/img/*", "/**").permitAll()
				.and().csrf()
					.disable();
	}
}

package com.wipro.ta.samplebank.configuration;

import javax.servlet.Filter;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.wipro.ta.samplebank.auth.AuthenticationFilter;

@Configuration
public class SpringWebMvcConfiguration extends WebMvcConfigurerAdapter {

	@Bean
	public InternalResourceViewResolver getInternalViewResolver() {
		InternalResourceViewResolver ivr = new InternalResourceViewResolver();
		ivr.setOrder(0);
		ivr.setPrefix("/WEB-INF/jsp/");
		ivr.setSuffix(".jsp");
		return ivr;
	}

	@Bean
	public FilterRegistrationBean someFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(getAuthenticationFilter());
		registration.addUrlPatterns("/*");
		registration.setOrder(1);
		return registration;
	}

	public Filter getAuthenticationFilter() {
		return new AuthenticationFilter();
	}
}
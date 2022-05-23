package com.ed1.article.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	
//	@Value("${upload.directory}")
//	private String uploadDirectory;
	
	@Bean
	public WebMvcConfigurer customConfigurer() {
	    return new WebMvcConfigurer() {
	        @Override
	        public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
	            configurer.defaultContentType(MediaType.APPLICATION_JSON);
	        }
	    };
	}
	
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//    	registry.addMapping("/**")
//        .allowedOrigins("*")
//        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
//    }
    
    @Override 
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
    	registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//    	registry.addResourceHandler("/resources/**").addResourceLocations("file:" + uploadDirectory);
    }
}
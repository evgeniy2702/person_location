package ua.ukrposhta.person_location.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/**.css").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/**.js").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/**.html").addResourceLocations("classpath:/templates/");
        registry.addResourceHandler("/**.properties").addResourceLocations("classpath:/properties/");
    }
}



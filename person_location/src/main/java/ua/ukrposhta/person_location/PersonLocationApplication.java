package ua.ukrposhta.person_location;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@SpringBootApplication(scanBasePackages = "ua.ukrposhta.person_location")
@EnableJpaRepositories
@EnableCaching
public class PersonLocationApplication
		extends AbstractAnnotationConfigDispatcherServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PersonLocationApplication.class, args);
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[0];
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[0];
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{
				"","/",
				"/person-location-data-filter/",
				"/person-location-data-filter"
		};
	}

    @Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public ClassLoaderTemplateResolver secondaryTemplateResolver() {
		ClassLoaderTemplateResolver secondaryTemplateResolver = new ClassLoaderTemplateResolver();
		secondaryTemplateResolver.setPrefix("templates/");
		secondaryTemplateResolver.setSuffix(".html");
		secondaryTemplateResolver.setTemplateMode(TemplateMode.HTML);
		secondaryTemplateResolver.setCharacterEncoding("UTF-8");
		secondaryTemplateResolver.setOrder(1);
		secondaryTemplateResolver.setCheckExistence(true);

		return secondaryTemplateResolver;
	}
}

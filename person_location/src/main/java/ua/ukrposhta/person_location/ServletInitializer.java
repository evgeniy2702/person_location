package ua.ukrposhta.person_location;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletInitializer extends SpringBootServletInitializer {

// THIS SET UP WAS USING FOR JFILECHOOSER LIB, THAT WILL BE PLANE TO EXPAND ON THE CATALINA.TOMCAT,
// BECAUSE GET ERROR PAGE FILTER
    public ServletInitializer() {
        super();
        setRegisterErrorPageFilter(false);
    }

// THIS SET UP WAS USING FOR JFILECHOOSER LIB, THAT WILL BE PLANE TO EXPAND ON THE CATALINA.TOMCAT,
// BECAUSE GET HEADENLESS EXCEPTION
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PersonLocationApplication.class)
				.web(WebApplicationType.NONE)
				.headless(false)
				.bannerMode(Mode.OFF);
	}

}

package ua.ukrposhta.person_location.geocoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.ukrposhta.person_location.controller.PersonController;

@Component
public class Geocoder {

    private Logger logger = LoggerFactory.getLogger(Geocoder.class);
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    private static final String GEOCODING_RESOURCE = "https://nominatim.openstreetmap.org/reverse";

    public String GeocodeSync(String lat, String lng) {

        logger.info("Start GeocodeSync method in Geocoder.class");

        String requestUri = GEOCODING_RESOURCE + "?format=jsonv2&lat=" + lat + "&lon=" + lng;

        ResponseEntity<String> response = restTemplate.getForEntity(
                requestUri,
                String.class
        );

        logger.info("response status : " + response.getStatusCode().toString());

        return response.getBody();
    }
}
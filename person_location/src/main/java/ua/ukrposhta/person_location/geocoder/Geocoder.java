package ua.ukrposhta.person_location.geocoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.ukrposhta.person_location.utils.ConsoleLogger;

@Component
public class Geocoder {

    private ConsoleLogger logger = ConsoleLogger.getInstance();
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

        logger.info("response : " + response.getBody());
        logger.info("response status : " + response.getStatusCode().toString());

        return response.getBody();
    }
}
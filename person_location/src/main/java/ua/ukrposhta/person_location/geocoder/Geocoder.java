package ua.ukrposhta.person_location.geocoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Geocoder {

    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    private static final String GEOCODING_RESOURCE = "https://nominatim.openstreetmap.org/reverse";

    public String GeocodeSync(String lat, String lng) {

        String requestUri = GEOCODING_RESOURCE + "?format=jsonv2&lat=" + lat + "&lon=" + lng;

        ResponseEntity<String> response = restTemplate.getForEntity(
                requestUri,
                String.class
        );

        return response.getBody();
    }
}
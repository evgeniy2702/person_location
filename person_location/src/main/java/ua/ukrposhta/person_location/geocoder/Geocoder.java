package ua.ukrposhta.person_location.geocoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.util.Objects;

@Component
public class Geocoder {

    private Logger logger = LoggerFactory.getLogger(Geocoder.class);
    private RestTemplate restTemplate;
    private WebClient webClient;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate,
                                WebClient webClient) {
        this.restTemplate = restTemplate;
        this.webClient = webClient;
    }


    private static final String GEOCODING_RESOURCE = "https://nominatim.openstreetmap.org/reverse";

    public String GeocodeSync(String lat, String lng) {

        logger.info("Start GeocodeSync method in Geocoder.class");

        String requestUri = GEOCODING_RESOURCE + "?format=jsonv2&lat=" + lat + "&lon=" + lng;

        ResponseEntity<String> responseWebClient = Objects.requireNonNull(webClient.get()
                .uri(URI.create(requestUri))
                .retrieve()
                .toEntity(String.class))
                .block();

//        ResponseEntity<String> response = restTemplate.getForEntity(
//                requestUri,
//                String.class
//        );

        logger.info("response status : " + Objects.requireNonNull(responseWebClient).getStatusCode().toString());

        return responseWebClient.getBody();
    }
}
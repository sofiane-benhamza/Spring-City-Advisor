package all.cities;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GetAirQuality {

    private final RestTemplate restTemplate;
    private final String accessToken = "7zgKwYyIgGuZrzGLrgXu7w==2cS8hhmx3Ne3O3wt";

    public GetAirQuality(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping("/airquality")
    public ResponseEntity<String> fetchAirQualityFromAPI(
            @RequestParam(name = "city", defaultValue = "rabat") String cityName) {
        String apiUrl = "https://api.api-ninjas.com/v1/airquality?city=" + cityName;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    entity,
                    String.class);

            return responseEntity; // Return the entire response entity

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }
}

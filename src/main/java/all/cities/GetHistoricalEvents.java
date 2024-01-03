package all.cities;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GetHistoricalEvents {

    private final RestTemplate restTemplate;
    private final String apiKey = "7zgKwYyIgGuZrzGLrgXu7w==2cS8hhmx3Ne3O3wt"; // Replace with your actual API key

    public GetHistoricalEvents(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping("/historical")
    public ResponseEntity<String> fetchHistoricalData(
            @RequestParam(name = "city", defaultValue = "rabat") String cityName) {
        String apiUrl = "https://api.api-ninjas.com/v1/historicalevents?text=" + cityName; // Replace with the correct
                                                                                           // historical API
        // URL

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);

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

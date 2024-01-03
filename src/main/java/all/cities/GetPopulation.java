package all.cities;

import org.springframework.http.HttpHeaders;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

@RestController
public class GetPopulation {

    private final RestTemplate restTemplate;
    private final String accessToken = "diU6NjNqIcbp9WZSO/ZNQQ==zolVhVzwqKacqMw3"; // access token

    public GetPopulation(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/population")
    public String fetchWeatherFromAPI(@RequestParam(name = "city", defaultValue = "rabat") String cityName) {
        String apiUrl = "https://api.api-ninjas.com/v1/city?name=" + cityName;

        try {
            // Adding headers for the access token
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-api-key", accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    entity,
                    String.class);

            String response = responseEntity.getBody();

            if (response != null && response.trim().startsWith("[")) {
                // Parse JSON array response
                JSONArray jsonArray = new JSONArray(response);

                if (jsonArray.length() > 0) {
                    // Taking the first object from the array
                    JSONObject current = jsonArray.getJSONObject(0);

                    JSONObject extractedFields = new JSONObject();
                    extractedFields.put("population", current.optInt("population"));
                    extractedFields.put("country", current.optString("country"));
                    extractedFields.put("is_capital", current.optBoolean("is_capital"));

                    // Return extracted fields as JSON string
                    return extractedFields.toString();
                } else {
                    return "No data found for the city: " + cityName;
                }
            } else {
                return "Invalid or empty JSON array response received for the city: " + cityName;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }
}

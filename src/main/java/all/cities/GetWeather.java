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
import org.json.JSONObject;

@RestController
public class GetWeather {

    private final RestTemplate restTemplate;
    private final String accessToken = "4162e2dfa3mshf0649fbee3b7ddcp12cf7fjsn70630caf6ddb"; // Replace with your actual
                                                                                             // access token

    public GetWeather(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    
    @CrossOrigin(origins = "*")
    @GetMapping("/weather")
    public String fetchWeatherFromAPI(@RequestParam(name = "city", defaultValue = "pecso") String cityName) {
        String apiUrl = "https://weatherapi-com.p.rapidapi.com/current.json?q=" + cityName;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", accessToken); // adding header for access token

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                String.class);

        String response = responseEntity.getBody();

        JSONObject jsonObject = new JSONObject(response);

        // Extracting values
        JSONObject current = jsonObject.getJSONObject("current");

        // reCreate json
        JSONObject extractedFields = new JSONObject();
        extractedFields.put("temp_c", current.getDouble("temp_c"));
        extractedFields.put("is_day", current.getInt("is_day"));
        extractedFields.put("wind_kph", current.getDouble("wind_kph"));
        extractedFields.put("humidity", current.getInt("humidity"));
        extractedFields.put("cloud", current.getInt("cloud"));
        extractedFields.put("gust_kph", current.getDouble("gust_kph"));
        extractedFields.put("vis_km", current.getDouble("vis_km"));
        extractedFields.put("icon", "https:" + current.getJSONObject("condition").getString("icon"));

        // You can process or return the response as needed
        return extractedFields.toString();
    }
}

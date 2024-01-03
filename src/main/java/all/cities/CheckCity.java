package all.cities;

import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.exceptions.CsvValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CheckCity {

    @Autowired
    private ResourceLoader resourceLoader;

    @CrossOrigin(origins = "*")
    @GetMapping("/check")
    public List<String> doesCityExist(@RequestParam(name = "city", defaultValue = "") String cityName)
            throws CsvValidationException, IOException {
        List<List<String>> citiesData = readCitiesDataFromFile();
        cityName = "\""+cityName+"\"";
        for (List<String> cityData : citiesData) {
            if (cityData.contains(cityName.toLowerCase())) {
                List<String> cleanedCityData = new ArrayList<>();
                for (String data : cityData) {
                    cleanedCityData.add(data.replace("\"", ""));
                }
                return cleanedCityData;
            }
            
        }
        return null; // City not found in the data
    }

    protected List<List<String>> readCitiesDataFromFile() throws IOException {
        List<List<String>> citiesData = new ArrayList<>();

        try {
            Resource resource = new ClassPathResource("imports/cities.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineInArray = line.split(",");

                List<String> tmp = new ArrayList<>();
                tmp.add(lineInArray[0].toLowerCase());
                tmp.add(lineInArray[2].toLowerCase());
                tmp.add(lineInArray[3].toLowerCase());
                tmp.add(lineInArray[5].toLowerCase());

                citiesData.add(tmp);
            }

            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading CSV: " + e.getMessage());
            throw e;
        }

        return citiesData;
    }
}

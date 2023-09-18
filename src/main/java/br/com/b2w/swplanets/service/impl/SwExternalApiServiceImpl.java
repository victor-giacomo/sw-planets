package br.com.b2w.swplanets.service.impl;

import br.com.b2w.swplanets.exceptions.BusinessException;
import br.com.b2w.swplanets.exceptions.MoreThenOnePlanetFoundException;
import br.com.b2w.swplanets.exceptions.PlanetIncorrectNameException;
import br.com.b2w.swplanets.exceptions.PlanetNotFoundException;
import br.com.b2w.swplanets.service.SwExternalApiService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SwExternalApiServiceImpl implements SwExternalApiService {

    private final String rootUri = "https://swapi.dev/api";
    private final String planetsUri = rootUri + "/planets";

    @Autowired
    private RestTemplate restTemplate;

    public JSONObject findOneByName(String planetName) throws BusinessException {

        final String findPlanetUri = planetsUri + "?search=" + planetName;

        String findResult = restTemplate.getForObject(findPlanetUri, String.class);

        if(findResult == null) {
            throw new PlanetNotFoundException("You have to use one Star War Planet Name");
        }

        JSONObject jsonResult = new JSONObject(findResult);
        int countPlanets = jsonResult.getInt("count");

        if(countPlanets == 0) {
            throw new PlanetNotFoundException("You have to use one Star War Planet Name");
        }

        if(countPlanets > 1) {
            throw new MoreThenOnePlanetFoundException("You have to use a Full Name of a Star War Planet");
        }

        JSONArray jsonResults = jsonResult.getJSONArray("results");
        JSONObject jsonPlanet = jsonResults.getJSONObject(0);
        String jsonPlanetName = jsonPlanet.getString("name");

        if(!planetName.equalsIgnoreCase(jsonPlanetName) ){
            throw new PlanetIncorrectNameException("Did you mean '" + jsonPlanetName + "'?");
        }

        return jsonPlanet;
    }

    public Integer getAmountFilms(String planetName) throws BusinessException {
        JSONObject jsonPlanet = findOneByName(planetName);
        return jsonPlanet.getJSONArray("films").length();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            request.getHeaders().add("user-agent", "Application");
            return execution.execute(request, body);
        };
        return restTemplateBuilder.additionalInterceptors(interceptor).build();
    }
}

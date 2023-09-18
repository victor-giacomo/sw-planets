package br.com.b2w.swplanets.service;

import br.com.b2w.swplanets.exceptions.BusinessException;
import br.com.b2w.swplanets.exceptions.MoreThenOnePlanetFoundException;
import br.com.b2w.swplanets.exceptions.PlanetNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public interface SwExternalApiService {
    JSONObject findOneByName(String planetName) throws BusinessException;
    Integer getAmountFilms(String planetName) throws BusinessException;
}

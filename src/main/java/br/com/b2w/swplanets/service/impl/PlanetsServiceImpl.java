package br.com.b2w.swplanets.service.impl;

import br.com.b2w.swplanets.exceptions.BusinessException;
import br.com.b2w.swplanets.exceptions.PlanetNameConflictException;
import br.com.b2w.swplanets.model.Planet;
import br.com.b2w.swplanets.repository.PlanetsRepository;
import br.com.b2w.swplanets.service.PlanetsService;
import br.com.b2w.swplanets.service.SwExternalApiService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanetsServiceImpl implements PlanetsService {

    private SwExternalApiService swExternalApiService;
    private PlanetsRepository planetsRepository;

    public PlanetsServiceImpl(SwExternalApiService swExternalApiService, PlanetsRepository planetsRepository) {
        this.swExternalApiService = swExternalApiService;
        this.planetsRepository = planetsRepository;
    }

    public List<Planet> list() {
        return planetsRepository.findAll();
    }

    public Planet find(String planetName) {
        return planetsRepository.findByNameIgnoreCase(planetName);
    }

    public Planet save(String planetName) throws BusinessException {

        validateName(planetName);

        JSONObject jsonPlanet = swExternalApiService.findOneByName(planetName);
        Integer amountFilms = swExternalApiService.getAmountFilms(planetName);

        Planet planet = new Planet();
        planet.setName(planetName);
        planet.setClimate(jsonPlanet.getString("climate"));
        planet.setTerrain(jsonPlanet.getString("terrain"));
        planet.setAmountFilms(amountFilms);

        return planetsRepository.save(planet);
    }

    public Integer getAmountFilms(String planetName) throws BusinessException {
        Planet planet = find(planetName);
        if(planet == null) {
            return swExternalApiService.getAmountFilms(planetName);
        }
        return planet.getAmountFilms();
    }

    public void delete(String name) {
        planetsRepository.deleteById(name);
    }

    private void validateName(String planetName) throws PlanetNameConflictException {

        Planet planetFound = find(planetName);

        if(planetFound != null) {
            throw new PlanetNameConflictException("This Planet has Already Been Registered ");
        }
    }
}

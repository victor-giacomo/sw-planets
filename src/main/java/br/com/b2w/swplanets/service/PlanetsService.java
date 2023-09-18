package br.com.b2w.swplanets.service;

import br.com.b2w.swplanets.exceptions.BusinessException;
import br.com.b2w.swplanets.exceptions.PlanetNameConflictException;
import br.com.b2w.swplanets.model.Planet;

import java.util.List;

public interface PlanetsService {
    List<Planet> list();
    Planet find(String planetName);
    Planet save(String planetName) throws BusinessException;
    Integer getAmountFilms(String planetName) throws BusinessException;
    void delete(String id);
}
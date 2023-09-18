package br.com.b2w.swplanets.repository;

import br.com.b2w.swplanets.model.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetsRepository extends JpaRepository<Planet, String> {
	Planet findByNameIgnoreCase(String name);
}
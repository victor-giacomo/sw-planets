package br.com.b2w.swplanets.controller;

import br.com.b2w.swplanets.exceptions.BusinessException;
import br.com.b2w.swplanets.exceptions.PlanetNameConflictException;
import br.com.b2w.swplanets.model.Planet;
import br.com.b2w.swplanets.service.PlanetsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/")
public class PlanetsController {

	private PlanetsService planetsService;

	public PlanetsController(PlanetsService planetsService) {
		this.planetsService = planetsService;
	}

	@GetMapping("/planets")
	public List<Planet> list() {
		return planetsService.list();
	}

	@GetMapping("planet/{name}")
	public ResponseEntity<Planet> find(@PathVariable String name) {
        Planet planet = planetsService.find(name);
		if (planet != null) {
			return ResponseEntity.ok(planet);
		}
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/planet/{name}")
	@Transactional
	public ResponseEntity<?> save(@PathVariable String name, UriComponentsBuilder uriBuilder) {
		try {
			Planet planet = planetsService.save(name);
			URI uri = uriBuilder.path("/planet/{name}").buildAndExpand(name).toUri();
			return ResponseEntity.created(uri).body(planet);

		} catch (PlanetNameConflictException pnc) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(pnc.getMessage());
		} catch (BusinessException be) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(be.getMessage());
		} catch (ResourceAccessException ra) {
			return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(ra.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error!");
		}
	}

	@DeleteMapping("planet/{name}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable String name) {
		Planet planet = planetsService.find(name);
		if (planet != null) {
			planetsService.delete(name);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping("/planet/{name}/amount-films")
	public ResponseEntity<?> amountFilms(@PathVariable String name) {
		try {
			return ResponseEntity.ok( planetsService.getAmountFilms(name) );
		} catch (BusinessException me) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(me.getMessage());
		} catch (ResourceAccessException ra) {
			return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(ra.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error!");
		}
	}
}
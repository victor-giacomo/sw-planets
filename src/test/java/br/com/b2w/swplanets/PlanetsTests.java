package br.com.b2w.swplanets;

import br.com.b2w.swplanets.model.Planet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlanetsTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void saveTest() throws IOException {
		String planetNameTest = "Tatooine";
		Planet planetSaved = save(planetNameTest);

		assertThat(planetSaved).isNotNull();
		assertThat(planetSaved.getName()).isEqualTo(planetNameTest);
	}

	@Test
	public void listTest() {
		Planet[] planets = this.restTemplate.getForEntity("/planets", Planet[].class).getBody();
		assertThat(planets).isNotNull();
	}

	@Test
	public void findByNameTest() {
		String planetNameTest = "Tatooine";
		Planet planet = find(planetNameTest);

		assertThat(planet).isNotNull();
		assertThat(planet.getName()).isNotNull().isNotBlank();
		assertThat(planet.getClimate()).isNotNull().isNotBlank();
		assertThat(planet.getTerrain()).isNotNull().isNotBlank();
		assertThat(planet.getName()).isEqualTo(planetNameTest);
	}

	@Test
	public void amountFilmsTest() {
		Integer amountFilms = this.restTemplate.getForObject("/planet/Alderaan/amount-films", Integer.class);
		assertThat(amountFilms).isEqualTo(2);
	}

	@Test
	public void saveIncorrectNameTest() {
		ResponseEntity<?> response;
		response = this.restTemplate.postForEntity("/planet/incorrectName", null, String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void notFoundNameTest() {
		Planet planet = find("Impossible_Name");
		assertThat(planet).isNull();
	}

	@Test
	public void deleteTest() throws IOException {
		String planetNameTest = "Dagobah";
		Planet planet = save(planetNameTest);

		assertThat(planet).isNotNull();

		this.restTemplate.delete("/planet/" + planetNameTest);
		Planet foundedPlanet = find(planetNameTest);
		assertThat(foundedPlanet).isNull();
	}

	private Planet save(String planetNameTest) throws IOException {
		ResponseEntity<?> response;
		response = this.restTemplate.postForEntity("/planet/" + planetNameTest, null, String.class);

		return new ObjectMapper().readValue((String)response.getBody(), Planet.class);
	}

	private Planet find(String name) {
		return this.restTemplate.getForEntity("/planet/" + name, Planet.class).getBody();
	}
}

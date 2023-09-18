package br.com.b2w.swplanets.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "planet")
public class Planet {

	@Id @NotNull @NotEmpty
	private String name;
	@NotNull
	private String climate;
	@NotNull
	private String terrain;
	private Integer amountFilms;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Planet planet = (Planet) o;
		return Objects.equals(name, planet.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

	public Integer getAmountFilms() { return amountFilms; }

	public void setAmountFilms(Integer amountFilms) { this.amountFilms = amountFilms; }
}
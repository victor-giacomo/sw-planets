package br.com.b2w.swplanets.exceptions;

public class PlanetNotFoundException extends BusinessException {
    public PlanetNotFoundException(String message) {
        super(message);
    }
}

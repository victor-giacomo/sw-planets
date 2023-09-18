package br.com.b2w.swplanets.exceptions;

public class PlanetNameConflictException extends BusinessException {
    public PlanetNameConflictException(String message) {
        super(message);
    }
}

package br.com.b2w.swplanets.exceptions;

public class MoreThenOnePlanetFoundException extends BusinessException {
    public MoreThenOnePlanetFoundException(String message) {
        super(message);
    }
}

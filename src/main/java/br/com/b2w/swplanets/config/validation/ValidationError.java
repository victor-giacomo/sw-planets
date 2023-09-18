package br.com.b2w.swplanets.config.validation;

public class ValidationError {

    private String field;
    private String error;

    ValidationError(String field, String error) {
        this.field = field;
        this.error = error;
    }

    public String getField() {
        return field;
    }

    public String getError() {
        return error;
    }
}
package es.ticketcenture.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DniValidator implements ConstraintValidator<ValidDni, String> {

    private static final char[] LETRAS = {
        'T','R','W','A','G','M','Y','F','P','D','X',
        'B','N','J','Z','S','Q','V','H','L','C','K','E'
    };

    @Override
    public boolean isValid(String dni, ConstraintValidatorContext context) {
        if (dni == null || !dni.matches("\\d{8}[A-Za-z]")) {
            return false;
        }
        String numeroStr = dni.substring(0, 8);
        char letra = Character.toUpperCase(dni.charAt(8));
        int numero = Integer.parseInt(numeroStr);
        char letraCorrecta = LETRAS[numero % 23];
        return letra == letraCorrecta;
    }
}

package emt.emtau.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DiscountNotFoundException extends RuntimeException{
    public DiscountNotFoundException(Long id) {
        super(String.format("Discount with id: %d is not found", id));
    }
}

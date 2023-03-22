package webp.testau.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//za koga ke frli exception od status Not Found
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException (Long id){
        super(String.format("Category with id %d was not found", id));
    }
}

package com.paygoal.products.controller.handlerexception;

import com.paygoal.products.business.dto.response.ExceptionDto;
import com.paygoal.products.exception.IdNotFoundException;
import com.paygoal.products.exception.NameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameAlreadyBoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice(annotations = RestController.class)
public class ApiControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto exceptionHandler(MethodArgumentNotValidException ex) {
        List<FieldError> errorList = ex.getBindingResult().getFieldErrors();
        Map<String, String> detalle = new HashMap<>();
        errorList.forEach(x -> detalle.put(x.getField(), x.getDefaultMessage()));
        return new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Error de validación", detalle);
    }

    @ExceptionHandler(IdNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto idNotFound(IdNotFoundException ex) {
        return new ExceptionDto(HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null);
    }

    @ExceptionHandler(NameAlreadyExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto nameAlreadyExists(NameAlreadyExistsException ex) {
        return new ExceptionDto(HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null);
    }

}

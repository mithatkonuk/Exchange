package com.ozan.exchange.controllerAdvice;

import com.ozan.exchange.http.response.Response;
import com.ozan.exchange.http.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExChangeControllerAdvice
{

    @ExceptionHandler( ConstraintViolationException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    public @ResponseBody
    Response handleResourceNotFound( final ConstraintViolationException exception,
                    final HttpServletRequest request )
    {
        StringBuilder stringBuilder = new StringBuilder();
        // ExceptionUtils needs
        exception.getConstraintViolations().forEach(constraintViolation -> {
            stringBuilder.append(constraintViolation.getMessage()).append("-")
                            .append(constraintViolation.getPropertyPath()).append(",");
        });
        ResponseError responseError = ResponseError.builder().
                        errorCode(1).message(stringBuilder.toString()).build();
        Response response = Response.builder().error(responseError).build();
        return response;
    }
}

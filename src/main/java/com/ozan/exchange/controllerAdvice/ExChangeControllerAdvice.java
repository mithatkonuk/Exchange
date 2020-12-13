package com.ozan.exchange.controllerAdvice;

import com.ozan.exchange.error.ErrorCode;
import com.ozan.exchange.exception.ExternalServiceException;
import com.ozan.exchange.http.response.Response;
import com.ozan.exchange.http.response.ResponseError;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import static com.ozan.exchange.error.ErrorCode.EXTERNAL_SERVICE_PROVIDER.EXTERNAL_RESOURCE_EXCHANGE_NOT_FOUND;
import static com.ozan.exchange.error.ErrorCode.EXTERNAL_SERVICE_PROVIDER.METHOD_ARGUMENT_INVALID;

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
        return Response.builder().error(responseError).build();
    }

    @ExceptionHandler( ExternalServiceException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    public @ResponseBody
    Response handleResourceNotFound( final ExternalServiceException exception,
                    final HttpServletRequest request )
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("message:").append(exception.getMessage()).append(" ").append("-")
                        .append("error-code:").append(exception.getErrorCode().getErrorCode());

        ResponseError responseError = ResponseError.builder().
                        errorCode(exception.getErrorCode().getErrorCode())
                        .message(exception.getMessage()).description(stringBuilder.toString())
                        .build();
        return Response.builder().error(responseError).build();
    }

    @ExceptionHandler( FeignException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    public @ResponseBody
    Response handleResourceNotFound( final FeignException exception,
                    final HttpServletRequest request )
    {
        return buildMessage(exception.getMessage(), exception,
                        EXTERNAL_RESOURCE_EXCHANGE_NOT_FOUND);
    }

    @ExceptionHandler( MethodArgumentNotValidException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    public @ResponseBody
    Response handleResourceNotFound( final MethodArgumentNotValidException exception,
                    final HttpServletRequest request )
    {
        return buildMessage(exception.getMessage(), exception, METHOD_ARGUMENT_INVALID);
    }

    private Response buildMessage( String message, Exception exception, ErrorCode errorCode )
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("message:").append(message).append(" ").append("-").append(" ")
                        .append("error-code:").append(errorCode.getErrorCode());

        ResponseError responseError = ResponseError.builder().
                        errorCode(errorCode.getErrorCode()).description(stringBuilder.toString())
                        .build();
        return Response.builder().error(responseError).build();
    }
}

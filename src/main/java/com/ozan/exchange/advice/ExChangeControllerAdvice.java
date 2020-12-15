package com.ozan.exchange.advice;

import com.ozan.exchange.exception.ExchangeHistoryNotFoundException;
import com.ozan.exchange.exception.ExchangeServiceParamException;
import com.ozan.exchange.exception.ExternalServiceException;
import com.ozan.exchange.exception.error.ErrorCode;
import com.ozan.exchange.web.util.Response;
import com.ozan.exchange.web.util.ResponseError;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import static com.ozan.exchange.exception.error.ErrorCode.EXTERNAL_SERVICE_PROVIDER.EXTERNAL_RESOURCE_EXCHANGE_NOT_FOUND;
import static com.ozan.exchange.exception.error.ErrorCode.EXTERNAL_SERVICE_PROVIDER.METHOD_ARGUMENT_INVALID;
import static com.ozan.exchange.exception.error.ErrorCode.GENERIC.GENERIC_ERROR;

@ControllerAdvice
public class ExChangeControllerAdvice
{

    /// ---- Handle methods begin

    @ExceptionHandler( ConstraintViolationException.class )
    @ResponseStatus( value = HttpStatus.INTERNAL_SERVER_ERROR )
    public @ResponseBody
    Response handleConstraintViolationException( final ConstraintViolationException exception,
                    final HttpServletRequest request )
    {
        return buildMessage(exception, GENERIC_ERROR);
    }

    @ExceptionHandler( ExternalServiceException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    public @ResponseBody
    Response handleExternalServiceException( final ExternalServiceException exception,
                    final HttpServletRequest request )
    {
        return buildMessage(exception, exception.getErrorCode());
    }

    @ExceptionHandler( FeignException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    public @ResponseBody
    Response handleFeignException( final FeignException exception,
                    final HttpServletRequest request )
    {
        return buildMessage(exception, EXTERNAL_RESOURCE_EXCHANGE_NOT_FOUND);
    }

    @ExceptionHandler( { MethodArgumentNotValidException.class,
                    MissingServletRequestParameterException.class } )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    public @ResponseBody
    Response handleMethodArgumentNotValidException( final Exception exception,
                    final HttpServletRequest request )
    {
        return buildMessage(exception, METHOD_ARGUMENT_INVALID);
    }

    @ExceptionHandler( ExchangeServiceParamException.class )
    @ResponseStatus( value = HttpStatus.BAD_REQUEST )
    public @ResponseBody
    Response handleExchangeServiceParamException( final ExchangeServiceParamException exception,
                    final HttpServletRequest request )
    {
        return buildMessage(exception, exception.getErrorCode());
    }

    @ExceptionHandler( ExchangeHistoryNotFoundException.class )
    @ResponseStatus( value = HttpStatus.NO_CONTENT )
    public @ResponseBody
    Response handleExchangeServiceParamException( final ExchangeHistoryNotFoundException exception,
                    final HttpServletRequest request )
    {
        return Response.EMPTY_RESPONSE;
    }

    // --- Handle Method end

    private Response buildMessage( Exception exception, ErrorCode errorCode )
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[message]:").append(exception.getMessage()).append(" ").append("-")
                        .append(" ").append("[error-code]:").append(errorCode.getErrorCode());

        ResponseError responseError = ResponseError.builder().
                        errorCode(errorCode.getErrorCode()).description(stringBuilder.toString())
                        .build();
        return Response.builder().error(responseError).build();
    }
}

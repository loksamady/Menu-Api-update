package kh.com.csx.exception;

import jakarta.persistence.NoResultException;
import kh.com.csx.response.ErrorResponse;
import kh.com.csx.response.ErrorsResponse;
import kh.com.csx.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.FileNotFoundException;
import java.util.List;


@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Response handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleValidationExceptions(MethodArgumentNotValidException e) {
        ErrorsResponse errorsResponse = new ErrorsResponse(HttpStatus.BAD_REQUEST.value());
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        fieldErrors.forEach(fieldError -> {
            errorsResponse.addError(fieldError.getField(), fieldError.getDefaultMessage());
        });
        LOGGER.error(e.getMessage(), e);
        return errorsResponse;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleIllegalArgumentException(IllegalArgumentException e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<Response> handleApiException(APIException e) {
        LOGGER.error(e.getMessage(), e);
        Response errorResponse = new ErrorResponse(e.getResponseCode(), e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(e.getResponseCode()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Response handleAccessDeniedException(AccessDeniedException e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response handleForbiddenException(BadCredentialsException e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleNoHandlerException(NoHandlerFoundException e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleNoResourceFoundException(NoResourceFoundException e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleMethodValidationException(HandlerMethodValidationException e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase() + ": " + e.getMessage());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    @ExceptionHandler(NoResultException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleNoResultException(NoResultException e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleFileNotFoundException(FileNotFoundException e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        LOGGER.error("Data integrity violation: {}", e.getMessage(), e);

        String message = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();

        if (message != null && message.contains("Duplicate entry")) {
            // Extract the duplicate value
            String duplicateValue = message.replaceAll(".*Duplicate entry '([^']+)'.*", "$1");

            // Extract the column name (if needed)
            String column = message.replaceAll(".*for key '([^']+)'.*", "$1");

            return new ErrorResponse(
                    HttpStatus.CONFLICT.value(),
                    String.format("%s already exists for %s", duplicateValue, column)
            );
        }

        return new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Duplicate entry or constraint violation"
        );
    }
}

package cz.morosystems.users.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler({ EntityNotFoundException.class })
  public ResponseEntity<ApiErrorResponse> handleEntityNotFoundException(@NotNull EntityNotFoundException ex) {
    ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(apiErrorResponse);
  }

  @ExceptionHandler({ EntityExistsException.class })
  public ResponseEntity<ApiErrorResponse> handleEntityExistsException(@NotNull EntityExistsException ex) {
    ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(apiErrorResponse);
  }

  @ExceptionHandler({ MethodArgumentNotValidException.class})
  public ResponseEntity<ApiInvalidFieldsResponse> handleValidationExceptions(@NotNull MethodArgumentNotValidException ex) {
    /* Map<String, String> invalidFields = ex
        .getBindingResult()
        .getAllErrors()
        .stream()
        .collect(Collectors.toMap(err -> ((FieldError) err).getField(),
            ObjectError::getDefaultMessage
        ));
    */

    final Map<String, List<ObjectError>> map = ex
        .getBindingResult()
        .getAllErrors()
        .stream()
        .collect(Collectors.groupingBy(err -> ((FieldError) err).getField()));

    final List<ApiInvalidField> invalidFields = map
        .entrySet()
        .stream()
        .map(me -> new ApiInvalidField(
            me.getKey(),
            me.getValue()
              .stream()
              .map(ObjectError::getDefaultMessage)
              .collect(Collectors.toList())))
        .collect(Collectors.toList());

    ApiInvalidFieldsResponse apiInvalidFieldsResponse = new ApiInvalidFieldsResponse(
        HttpStatus.BAD_REQUEST.value(),
        invalidFields
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                         .contentType(MediaType.APPLICATION_JSON)
                         .body(apiInvalidFieldsResponse);
  }
}

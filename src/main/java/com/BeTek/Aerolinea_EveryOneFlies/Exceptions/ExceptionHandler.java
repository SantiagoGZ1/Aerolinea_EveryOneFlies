package com.BeTek.Aerolinea_EveryOneFlies.Exceptions;import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
//solo throw
@ControllerAdvice
public class ExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler({GeneralException.class})
  public ResponseEntity<Object> handleStudentExceptions(GeneralException e) {
    HttpStatus badRequest = HttpStatus.BAD_REQUEST;
    ApiException apiException = new ApiException(e.getMessage(), badRequest, e, ZonedDateTime.now(ZoneId.of("Z")));
    return new ResponseEntity(apiException, badRequest);
  }
}

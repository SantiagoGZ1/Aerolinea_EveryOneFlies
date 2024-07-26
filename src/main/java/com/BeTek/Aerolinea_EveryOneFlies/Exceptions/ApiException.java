package com.BeTek.Aerolinea_EveryOneFlies.Exceptions;
import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;

public class ApiException {
  private final String message;
  private final HttpStatus httpStatus;
  private final ZonedDateTime zonedDateTime;

  public ApiException(String message, HttpStatus httpStatus, GeneralException e, ZonedDateTime zonedDateTime) {
    this.message = message;
    this.httpStatus = httpStatus;
    this.zonedDateTime = zonedDateTime;
  }

  public String getMessage() {
    return this.message;
  }

  public HttpStatus getHttpStatus() {
    return this.httpStatus;
  }

  public ZonedDateTime getZonedDateTime() {
    return this.zonedDateTime;
  }
}

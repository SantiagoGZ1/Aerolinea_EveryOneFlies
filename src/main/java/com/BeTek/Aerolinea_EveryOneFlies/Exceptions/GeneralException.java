package com.BeTek.Aerolinea_EveryOneFlies.Exceptions;

public class GeneralException extends RuntimeException{
  public GeneralException(String message) {
    super(message);
  }

  public GeneralException(String message, Throwable cause) {
    super(message, cause);
  }
}

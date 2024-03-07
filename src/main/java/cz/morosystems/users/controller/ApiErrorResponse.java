package cz.morosystems.users.controller;

import jakarta.validation.constraints.NotNull;

public class ApiErrorResponse {

  private final int code;
  private final String message;

  public ApiErrorResponse(@NotNull int code, @NotNull String message) {
    this.code = code;
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public int getCode() {
    return code;
  }
}

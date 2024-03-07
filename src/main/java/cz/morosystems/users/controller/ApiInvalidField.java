package cz.morosystems.users.controller;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public class ApiInvalidField {

  private final String field;
  private final List<String> errorMessages;

  @NotNull
  public ApiInvalidField(@NotNull String field, @NotNull List<String> errorMessages) {
    this.field = field;
    this.errorMessages = errorMessages;
  }

  public String getField() {
    return field;
  }

  public List<String> getErrorMessages() {
    return errorMessages;
  }
}
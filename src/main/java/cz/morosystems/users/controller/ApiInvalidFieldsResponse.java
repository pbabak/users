package cz.morosystems.users.controller;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public class ApiInvalidFieldsResponse {

  private final int code;
  private final List<ApiInvalidField> invalidFields;

  public ApiInvalidFieldsResponse(@NotNull int code, @NotNull List<ApiInvalidField> invalidFields) {
    this.code = code;
    this.invalidFields = invalidFields;
  }

  public int getCode() {
    return code;
  }

  public List<ApiInvalidField> getInvalidFields() {
    return invalidFields;
  }
}
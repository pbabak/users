package cz.morosystems.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequest {
  @NotBlank(message = "Name is mandatory")
  @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
  private String name;

  @NotBlank(message = "Username is mandatory")
  @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
  private String username;

  @NotBlank(message = "Password is mandatory")
  @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
  private String password;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }
}

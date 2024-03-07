package cz.morosystems.users.controller;

import java.util.List;

import cz.morosystems.users.dto.User;
import cz.morosystems.users.dto.UserRequest;
import cz.morosystems.users.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public")
public class PublicController {

  final UsersService usersService;

  public PublicController(final UsersService usersService) {
    this.usersService = usersService;
  }

  @GetMapping(value = "/users", produces = "application/json")
  public List<User> getUsers() {
    return usersService.getUsers();
  }

  @GetMapping(value = "/users/{id}", produces = "application/json")
  public User getUser(@PathVariable("id") final Long id) {
    return usersService.getUser(id);
  }

  @PostMapping(value = "/users", consumes = "application/json", produces = "application/json")
  @ResponseBody
  @ResponseStatus(value = HttpStatus.CREATED)
  public User createUser(@Valid @RequestBody UserRequest userRequest) {
    return usersService.createUser(userRequest);
  }

  @PutMapping(value = "/users/{id}", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public User updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserRequest userRequest) {
    return usersService.updateUser(id, userRequest);
  }
}

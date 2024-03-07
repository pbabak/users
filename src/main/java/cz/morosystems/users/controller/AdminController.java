package cz.morosystems.users.controller;

import cz.morosystems.users.service.UsersService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {

  final UsersService usersService;

  public AdminController(final UsersService usersService) {
    this.usersService = usersService;
  }

  @DeleteMapping(value = "/users/{id}", produces = "application/json")
  @ResponseBody
  public void deleteUser(@PathVariable("id") Long id) {
    usersService.deleteUser(id);
  }
}

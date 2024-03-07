package cz.morosystems.users.service;

import java.util.ArrayList;
import java.util.Optional;

import cz.morosystems.users.dao.UsersRepository;
import cz.morosystems.users.db.entity.UserVO;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  final UsersRepository usersRepository;

  public CustomUserDetailsService(final UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  @Override
  @NotNull
  public UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {
    Optional<UserVO> optionalUserVO = usersRepository.findByUsername(username);

    if (optionalUserVO.isEmpty()) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }

    UserVO userVO = optionalUserVO.get();

    return new User(userVO.getUsername(), userVO.getPassword(), new ArrayList<>());
  }
}
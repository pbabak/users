package cz.morosystems.users.service;

import java.util.List;
import java.util.Optional;

import cz.morosystems.users.dao.UsersRepository;
import cz.morosystems.users.db.entity.UserVO;
import cz.morosystems.users.dto.User;
import cz.morosystems.users.dto.UserRequest;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

  final UsersRepository usersRepository;

  public UsersService(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  @Transactional
  @NotNull
  public List<User> getUsers() {
    final List<UserVO> userVOs = usersRepository.findAll();
    return userVOs
        .stream()
        .map(this::map)
        .toList();
  }


  @Transactional
  @NotNull
  public User getUser(@NotNull Long id) throws EntityNotFoundException {
    final Optional<UserVO> userVOOptional = usersRepository.findById(id);

    if (userVOOptional.isEmpty()) {
      throw new EntityNotFoundException("User not found");
    }

    return map(userVOOptional.get());
  }

  private void checkUsername(@NotNull String username, @Nullable Long exceptId) throws EntityExistsException {
    final Optional<UserVO> optionalUserVO = usersRepository.findByUsername(username);

    if (optionalUserVO.isPresent() && (exceptId == null || !optionalUserVO.get().getId().equals(exceptId))) {
      throw new EntityExistsException("Username is not unique");
    }
  }

  @Transactional
  @NotNull
  public User createUser(@NotNull UserRequest user) {
    checkUsername(user.getUsername(), null);

    final UserVO userVO = new UserVO();
    fillUserVO(userVO, user);
    usersRepository.save(userVO);

    return map(userVO);
  }

  @Transactional
  @NotNull
  public User updateUser(@NotNull Long id, @NotNull UserRequest userRequest) throws EntityNotFoundException {
    Optional<UserVO> userVOOptional = usersRepository.findById(id);

    if (userVOOptional.isEmpty()) {
      throw new EntityNotFoundException("User not found");
    }

    checkUsername(userRequest.getUsername(), id);

    final UserVO userVO = userVOOptional.get();
    fillUserVO(userVO, userRequest);

    usersRepository.save(userVO);
    return map(userVO);
  }

  @Transactional
  public void deleteUser(@NotNull Long id) {
    Optional<UserVO> userVOOptional = usersRepository.findById(id);

    if (userVOOptional.isEmpty()) {
      throw new EntityNotFoundException("User not found");
    }

    UserVO userVO = userVOOptional.get();
    usersRepository.delete(userVO);
  }

  @NotNull
  private void fillUserVO(@NotNull UserVO userVO, @NotNull UserRequest userRequest) {
    userVO.setName(userRequest.getName());
    userVO.setUsername(userRequest.getUsername());

    // šifrování hesla pomocí BCrypt
    String encryptedPassword = new BCryptPasswordEncoder().encode(userRequest.getPassword());
    userVO.setPassword(encryptedPassword);
  }

  @NotNull
  private User map(@NotNull UserVO userVO) {
    User user = new User();
    user.setId(userVO.getId());
    user.setName(userVO.getName());
    user.setUsername(userVO.getUsername());
    user.setPassword(userVO.getPassword());
    return user;
  }
}

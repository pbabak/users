package cz.morosystems.users.dao;

import java.util.Optional;

import cz.morosystems.users.db.entity.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UserVO, Long> {

  Optional<UserVO> findByUsername(String username);
}

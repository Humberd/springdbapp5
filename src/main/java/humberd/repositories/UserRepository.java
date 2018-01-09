package humberd.repositories;

import humberd.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);

    @Transactional
    Long deleteByUsername(String username);
}

package vn.duynv.managementuser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.duynv.managementuser.entity.User;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Query("SELECT u.email FROM User u WHERE u.email IN :emails")
    List<String> findExistingEmail(@Param("emails") Set<String> emails);

    @Query("SELECT u.username FROM User u WHERE u.username IN :usernames")
    List<String> findExistingUsernames(@Param("usernames") Set<String> usernames);

}

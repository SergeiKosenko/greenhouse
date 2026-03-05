package ru.kosenko.greenhouse.users.repositories;



import jakarta.annotation.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kosenko.greenhouse.users.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(@Nullable Long id);
    Optional<User> findByEmail(String email);

    @Modifying
    @Query(value = "update users_roles set role_id = ?1 where user_id = ?2", nativeQuery = true)
    void changeRole(Long roleId, Long userId);

    @Modifying
    @Query(value = "update users set active = ?1 where id = ?2", nativeQuery = true)
    void changeActive(Boolean active, Long userId);

    @Modifying
    @Query("update User u set u.updatedAt = CURRENT_TIMESTAMP where u.id = ?1")
    void changeUpdateAt(Long userId);


}

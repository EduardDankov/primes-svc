package dev.dankov.primes.dao;

import dev.dankov.primes.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID>
{
    @Query("SELECT u FROM UserEntity u " +
        "WHERE u.username = :username")
    Optional<UserEntity> findByUsername(@Param("username") String username);
}

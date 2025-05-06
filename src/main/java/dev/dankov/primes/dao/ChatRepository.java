package dev.dankov.primes.dao;

import dev.dankov.primes.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, UUID>
{
    @Query("SELECT c FROM ChatEntity c " +
        "JOIN UserEntity u ON c.createdBy.id = u.id OR c.createdWith.id = u.id " +
        "WHERE u.id = :userId")
    List<ChatEntity> findAllByUserId(@Param("userId") UUID userId);

    @Query("SELECT c FROM ChatEntity c " +
        "JOIN UserEntity createdBy ON c.createdBy.id = createdBy.id " +
        "JOIN UserEntity createdWith ON c.createdWith.id = createdWith.id " +
        "WHERE (createdBy.id = :firstUser AND createdWith.id = :secondUser)" +
        "OR (createdBy.id = :secondUser AND createdWith.id = :firstUser)")
    Optional<ChatEntity> findByUserIds(@Param("firstUser") UUID firstUserId,
                                       @Param("secondUser") UUID secondUserId);

    @Query("SELECT c FROM ChatEntity c " +
        "JOIN UserEntity u ON c.createdBy.id = u.id OR c.createdWith.id = u.id " +
        "WHERE u.id = :userId AND c.id = :chatId")
    Optional<ChatEntity> findByUserIdAndChatId(@Param("userId") UUID userId,
                                               @Param("chatId") UUID chatId);
}

package dev.dankov.primes.dao;

import dev.dankov.primes.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID>
{
    @Query("SELECT m FROM MessageEntity m " +
        "JOIN ChatEntity c ON m.chat.id = c.id " +
        "WHERE c.id = :chatId")
    List<MessageEntity> findAllByChatId(@Param("chatId") UUID chatId);
}

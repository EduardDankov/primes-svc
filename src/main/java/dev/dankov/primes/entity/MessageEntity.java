package dev.dankov.primes.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "messages")
public class MessageEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "message_id")
    @Valid
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    @Valid
    private ChatEntity chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Valid
    private UserEntity user;

    @NotBlank
    @Column(name = "message", nullable = false)
    @Valid
    private String message;

    @NotNull
    @Column(name = "created_at", nullable = false)
    @Valid
    private Instant createdAt;

    public MessageEntity()
    {
    }

    @PrePersist
    public void prePersist()
    {
        createdAt = Instant.now();
    }

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public ChatEntity getChat()
    {
        return chat;
    }

    public void setChat(ChatEntity chat)
    {
        this.chat = chat;
    }

    public UserEntity getUser()
    {
        return user;
    }

    public void setUser(UserEntity user)
    {
        this.user = user;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Instant getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt)
    {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof MessageEntity))
        {
            return false;
        }
        MessageEntity that = (MessageEntity) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(chat, that.chat) &&
            Objects.equals(user, that.user) &&
            Objects.equals(message, that.message) &&
            Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, chat, user, message, createdAt);
    }

    @Override
    public String toString()
    {
        return "MessageEntity{" +
            "id=" + id +
            ", chat=" + chat +
            ", user=" + user +
            ", message='" + message + '\'' +
            ", createdAt=" + createdAt +
            '}';
    }
}

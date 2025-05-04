package dev.dankov.primes.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "chats")
public class ChatEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "chat_id")
    @Valid
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    @Valid
    private UserEntity createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_with", nullable = false)
    @Valid
    private UserEntity createdWith;

    public ChatEntity()
    {
    }

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public UserEntity getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy)
    {
        this.createdBy = createdBy;
    }

    public UserEntity getCreatedWith()
    {
        return createdWith;
    }

    public void setCreatedWith(UserEntity createdWith)
    {
        this.createdWith = createdWith;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof ChatEntity))
        {
            return false;
        }
        ChatEntity that = (ChatEntity) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdWith, that.createdWith);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, createdBy, createdWith);
    }

    @Override
    public String toString()
    {
        return "ChatEntity{" +
            "id=" + id +
            ", createdBy=" + createdBy +
            ", createdWith=" + createdWith +
            '}';
    }
}

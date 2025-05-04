CREATE TABLE IF NOT EXISTS primes.messages
(
    message_id UUID                        NOT NULL,
    chat_id    UUID                        NOT NULL,
    user_id    UUID                        NOT NULL,
    message    VARCHAR(255)                NOT NULL,
    created_at TIMESTAMP                   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_messages PRIMARY KEY (message_id)
);

ALTER TABLE primes.messages ADD CONSTRAINT FK_MESSAGES_ON_CHAT FOREIGN KEY (chat_id) REFERENCES primes.chats (chat_id);
ALTER TABLE primes.messages ADD CONSTRAINT FK_MESSAGES_ON_USER FOREIGN KEY (user_id) REFERENCES primes.users (user_id);

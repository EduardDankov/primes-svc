CREATE TABLE IF NOT EXISTS primes.chats
(
    chat_id      UUID NOT NULL,
    created_by   UUID NOT NULL,
    created_with UUID NOT NULL,
    CONSTRAINT pk_chats PRIMARY KEY (chat_id)
);

ALTER TABLE primes.chats ADD CONSTRAINT FK_CHATS_ON_CREATED_BY FOREIGN KEY (created_by) REFERENCES primes.users (user_id);
ALTER TABLE primes.chats ADD CONSTRAINT FK_CHATS_ON_CREATED_WITH FOREIGN KEY (created_with) REFERENCES primes.users (user_id);

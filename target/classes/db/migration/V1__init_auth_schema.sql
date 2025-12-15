-- ============================
-- USERS
-- ============================
CREATE TABLE users (
    id UUID PRIMARY KEY,
    email TEXT UNIQUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- ============================
-- USER IDENTITIES (OAuth mapping)
-- ============================
CREATE TABLE user_identities (
     id UUID PRIMARY KEY,
     user_id UUID NOT NULL,
     provider TEXT NOT NULL,
     provider_subject TEXT NOT NULL,
     created_at TIMESTAMPTZ NOT NULL DEFAULT now(),

     CONSTRAINT fk_user_identities_user
         FOREIGN KEY (user_id)
             REFERENCES users (id)
             ON DELETE CASCADE,

     CONSTRAINT uq_user_provider
         UNIQUE (provider, provider_subject)
);

CREATE INDEX idx_user_identities_user_id
    ON user_identities (user_id);

-- ============================
-- REFRESH TOKENS
-- ============================
CREATE TABLE refresh_tokens (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    client_id TEXT NOT NULL,
    token_hash TEXT NOT NULL,
    expires_at TIMESTAMPTZ NOT NULL,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),

    CONSTRAINT fk_refresh_tokens_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);

CREATE INDEX idx_refresh_tokens_user_id
    ON refresh_tokens (user_id);

CREATE INDEX idx_refresh_tokens_token_hash
    ON refresh_tokens (token_hash);

CREATE INDEX idx_refresh_tokens_expires_at
    ON refresh_tokens (expires_at);
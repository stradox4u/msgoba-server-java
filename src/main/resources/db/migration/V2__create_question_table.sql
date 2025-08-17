CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE question (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    question VARCHAR(255) NOT NULL,
    options VARCHAR(50)[] NOT NULL,
    correct_option INTEGER NOT NULL
);

ALTER TABLE proto_profile
ALTER COLUMN id SET DEFAULT gen_random_uuid();
CREATE TABLE proto_profile (
    id UUID NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    date_of_birth TIMESTAMP(6) NOT NULL,
    nickname VARCHAR(50),
    phone_number VARCHAR(255) NOT NULL,
    created_at TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6),
    PRIMARY KEY (id)
)
CREATE TABLE questions_verified (
    id SERIAL PRIMARY KEY,
    user_id UUID,
    created_at TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6)
);

CREATE INDEX idx_questions_verified_user_id ON questions_verified (user_id);

CREATE TABLE profile (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    occupation_status VARCHAR(255) NOT NULL,
    occupation VARCHAR(255) NOT NULL,
    place_of_work VARCHAR(255),
    place_of_residence VARCHAR(255) NOT NULL,
    hobbies VARCHAR(50)[] NOT NULL,
    birthday DATE NOT NULL,
    marital_status VARCHAR(50) NOT NULL,
    nickname VARCHAR(50),
    final_class VARCHAR(50) NOT NULL,
    exco_position VARCHAR(50) DEFAULT 'none',
    bio TEXT,
    profile_picture_url VARCHAR(255),
    created_at TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(6)
);

CREATE INDEX idx_profile_user_id ON profile (user_id);
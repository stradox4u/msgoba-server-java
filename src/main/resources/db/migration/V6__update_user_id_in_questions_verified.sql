ALTER TABLE questions_verified
ALTER COLUMN user_id TYPE VARCHAR(255);

ALTER TABLE questions_verified
ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE questions_verified
ADD CONSTRAINT unique_user_id UNIQUE (user_id);
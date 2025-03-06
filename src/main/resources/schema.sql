DROP TABLE IF EXISTS question_categories;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS questions;

CREATE TABLE questions (
    id SERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    example_answer TEXT,
    detailed_content TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    code VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE question_categories (
    question_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (question_id, category_id),
    FOREIGN KEY (question_id) REFERENCES questions(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
); 